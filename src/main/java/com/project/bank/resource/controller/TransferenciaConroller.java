package com.project.bank.resource.controller;

import com.project.bank.entity.dto.TransferenciaDto;
import com.project.bank.entity.form.TransferenciaForm;
import com.project.bank.entity.model.Transferencia;
import com.project.bank.service.implementation.TransferenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/bank/transferencias")
@RequiredArgsConstructor
public class TransferenciaConroller
{
    @Autowired
    private TransferenciaService transferenciaService;
    @PostMapping
    public ResponseEntity<TransferenciaDto> realizarTransferencia(@RequestBody @Valid TransferenciaForm transferencia, Principal principal)
    {
        return ResponseEntity.ok(transferenciaService.realizarTransferencia(transferencia, principal.getName()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<TransferenciaDto> obterTransferencia(@PathVariable("id") long id)
    {
        return ResponseEntity.ok(transferenciaService.obterTransferencia(id));
    }
    @GetMapping("/all")
    public ResponseEntity<List<TransferenciaDto>> obterTransferenciasCliente(Principal principal)
    {
        return ResponseEntity.ok(transferenciaService.obterTransferenciasCliente(principal.getName()));
    }
}
