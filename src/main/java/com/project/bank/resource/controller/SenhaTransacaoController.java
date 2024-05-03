package com.project.bank.resource.controller;

import com.project.bank.entity.form.SenhaTransacaoPostForm;
import com.project.bank.entity.form.SenhaTransacaoPutForm;
import com.project.bank.entity.model.SenhaTransacao;
import com.project.bank.service.implementation.SenhaTransacaoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank/senhaTransacao")
@AllArgsConstructor
public class SenhaTransacaoController {
    private final SenhaTransacaoService senhaTransacaoService;

    @PostMapping
    public SenhaTransacao cadastrarSenhaTransacao(@RequestBody SenhaTransacaoPostForm senhaTransacao)
    {
        return senhaTransacaoService.cadastrarSenhaTransacao(senhaTransacao);
    }

    @PatchMapping
    public SenhaTransacao atualizarSenhaTransacao(@RequestBody SenhaTransacaoPutForm senhaTransacao)
    {
        return senhaTransacaoService.atualizarSenhaTransacao(senhaTransacao);
    }
}
