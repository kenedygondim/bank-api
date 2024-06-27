package com.project.bank.resource.controller;
import com.project.bank.entity.form.SenhaTransacaoPostForm;
import com.project.bank.entity.form.SenhaTransacaoPutForm;
import com.project.bank.entity.model.SenhaTransacao;
import com.project.bank.service.implementation.SenhaTransacaoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
@RestController
@RequestMapping("/bank/senhaTransacao")
@AllArgsConstructor
public class SenhaTransacaoController
{
    @Autowired
    private SenhaTransacaoService senhaTransacaoService;
    @PostMapping
    public ResponseEntity<String> cadastrarSenhaTransacao(@RequestBody @Valid SenhaTransacaoPostForm senhaTransacao, Principal principal)
    {
        return ResponseEntity.ok(senhaTransacaoService.cadastrarSenhaTransacao(senhaTransacao, principal.getName()));
    }
    @PatchMapping
    public SenhaTransacao atualizarSenhaTransacao(@RequestBody @Valid SenhaTransacaoPutForm senhaTransacao, Principal principal)
    {
        return senhaTransacaoService.atualizarSenhaTransacao(senhaTransacao, principal.getName());
    }
}
