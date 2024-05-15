package com.project.bank.resource.controller;

import com.project.bank.entity.form.AuthForm;
import com.project.bank.entity.model.Usuario;
import com.project.bank.enumeration.SolicitacaoConta;
import com.project.bank.handler.BusinessException;
import com.project.bank.repository.UsuarioRepository;
import com.project.bank.security.TokenService;
import com.project.bank.service.implementation.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("bank/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final AuthenticationManager authManager;
    private final UsuarioRepository usuarioRepository;
    private final ContaService contaService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AuthForm form)
    {
        Usuario usuario = usuarioRepository.findFirstByCpf(form.cpf());

        if(usuario == null)
            throw new BusinessException("CPF inválido.");
        if(usuario.getSolicitacaoConta() == SolicitacaoConta.PENDENTE || usuario.getSolicitacaoConta() == SolicitacaoConta.RECUSADA)
            throw new BusinessException("Sua solicitação de conta está pendente ou foi recusada.");

        var usuarioCredentials =  new UsernamePasswordAuthenticationToken(form.cpf(), form.senha());
        var auth = authManager.authenticate(usuarioCredentials);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(token);
    }
}
