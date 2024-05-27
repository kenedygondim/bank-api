package com.project.bank.resource.controller;
import com.project.bank.entity.form.SenhaTransacaoPostForm;
import com.project.bank.entity.form.SenhaTransacaoPutForm;
import com.project.bank.entity.model.SenhaTransacao;
import com.project.bank.service.implementation.SenhaTransacaoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
@RestController
@RequestMapping("/bank/senhaTransacao")
@AllArgsConstructor
public class SenhaTransacaoController
{
    private final SenhaTransacaoService senhaTransacaoService;
    @PostMapping
    public SenhaTransacao cadastrarSenhaTransacao(@RequestBody @Valid SenhaTransacaoPostForm senhaTransacao, Principal principal)
    {
        return senhaTransacaoService.cadastrarSenhaTransacao(senhaTransacao, principal.getName());
    }
    @PatchMapping
    public SenhaTransacao atualizarSenhaTransacao(@RequestBody @Valid SenhaTransacaoPutForm senhaTransacao, Principal principal)
    {
        return senhaTransacaoService.atualizarSenhaTransacao(senhaTransacao, principal.getName());
    }
}
