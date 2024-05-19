package com.project.bank.resource.controller;

import com.project.bank.entity.form.SolicitacaoContaForm;
import com.project.bank.service.implementation.SolicitacaoContaService;
import com.project.bank.service.implementation.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bank/solicitarConta")
@RequiredArgsConstructor
public class SolicitacaoContaController
{
    private final SolicitacaoContaService solicitacaoContaService;

    @PostMapping
    public ResponseEntity<String> solicitarConta(@RequestBody @Valid SolicitacaoContaForm formUsuario)
    {
        solicitacaoContaService.solicitarConta(formUsuario);
        return ResponseEntity.ok("Conta solicitada com sucesso!");
    }

    /*@PostMapping("/contaAdmin")
    public void contaAdmin()
    {
        solicitacaoContaService.contaAdmin();
    }*/
}
