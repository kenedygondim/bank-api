package com.project.bank.resource.controller;

import com.project.bank.entity.dto.TransferenciaDto;
import com.project.bank.entity.model.Transferencia;
import com.project.bank.service.implementation.TransferenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bank/transferencias")
@RequiredArgsConstructor
public class TransferenciaConroller {

    private final TransferenciaService transferenciaService;

    @GetMapping("/{id}")
    public ResponseEntity<TransferenciaDto> obterTransferencia(@PathVariable("id") long id)
    {
        return ResponseEntity.ok(transferenciaService.obterTransferencia(id));
    }

    @GetMapping("/{id}/all")
    public ResponseEntity<List<TransferenciaDto>> obterTransferenciasCliente(@PathVariable("id") long id)
    {
        return ResponseEntity.ok(transferenciaService.obterTransferenciasCliente(id));
    }
}
