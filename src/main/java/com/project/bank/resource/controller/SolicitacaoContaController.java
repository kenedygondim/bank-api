package com.project.bank.resource.controller;

import com.project.bank.entity.dto.SolicitacaoContaDto;
import com.project.bank.entity.form.SolicitacaoContaForm;
import com.project.bank.entity.model.SolicitacaoConta;
import com.project.bank.service.implementation.SolicitacaoContaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank/solicitacaoConta")
@RequiredArgsConstructor
public class SolicitacaoContaController
{
    @Autowired
    private SolicitacaoContaService solicitacaoContaService;

    @PostMapping
    public ResponseEntity<String> solicitarConta(@RequestBody @Valid SolicitacaoContaForm formUsuario)
    {
        return ResponseEntity.ok(solicitacaoContaService.solicitarConta(formUsuario));
    }
    @GetMapping("/solicitacoes")
    public ResponseEntity<List<SolicitacaoConta>> obterSolicitacoes()
    {
        return ResponseEntity.ok(solicitacaoContaService.obterSolicitacoes());
    }
    @PostMapping("/contaAdmin")
    public void contaAdmin()
    {
        solicitacaoContaService.contaAdmin();
    }
}
