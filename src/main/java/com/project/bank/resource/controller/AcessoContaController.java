package com.project.bank.resource.controller;
import com.project.bank.entity.form.AcessoContaForm;
import com.project.bank.entity.model.AcessoConta;
import com.project.bank.handler.BusinessException;
import com.project.bank.repository.AcessoContaRepository;
import com.project.bank.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("bank/auth")
@RequiredArgsConstructor
public class AcessoContaController
{
    private final AuthenticationManager authManager;
    private final AcessoContaRepository acessoContaRepository;
    private final TokenService tokenService;
    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AcessoContaForm form)
    {
        AcessoConta acessoConta = acessoContaRepository.findFirstByLogin(form.login());
        if(acessoConta == null)
            throw new BusinessException("Login inválido.");
        var acessoContaCredentials =  new UsernamePasswordAuthenticationToken(form.login(), form.senha());
        var auth = authManager.authenticate(acessoContaCredentials);
        var token = tokenService.generateToken((AcessoConta) auth.getPrincipal());
        return ResponseEntity.ok(token);
    }
}
