package com.project.bank.resource.controller;

import com.project.bank.entity.form.AuthForm;
import com.project.bank.entity.form.RegisterForm;
import com.project.bank.entity.model.Cliente;
import com.project.bank.entity.model.Conta;
import com.project.bank.handler.BusinessException;
import com.project.bank.repository.ClienteRepository;
import com.project.bank.security.TokenService;
import com.project.bank.service.implementation.ContaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("bank/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final AuthenticationManager authManager;
    private final ClienteRepository clienteRepository;
    private final ContaService contaService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AuthForm form)
    {
        var clienteSenha =  new UsernamePasswordAuthenticationToken(form.cpf(), form.senha());
        var auth = authManager.authenticate(clienteSenha);

        var token = tokenService.generateToken((Cliente) auth.getPrincipal());

        return ResponseEntity.ok(token);
    }
}
