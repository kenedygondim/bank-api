package com.project.bank.resource.controller;

import com.project.bank.entity.form.EnderecoForm;
import com.project.bank.entity.model.Endereco;
import com.project.bank.service.implementation.EnderecoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping("/bank/endereco")
public class EnderecoController
{
    private final EnderecoService enderecoService;
    @PostMapping
    public ResponseEntity<Endereco> cadastrarEndereco(@RequestBody @Valid EnderecoForm endereco, Principal principal)
    {
        return ResponseEntity.ok(enderecoService.cadastrarEndereco(endereco, principal.getName()));
    }
    @GetMapping
    public ResponseEntity<Endereco> obterEnderecoPeloCpf(Principal principal)
    {
        return ResponseEntity.ok(enderecoService.obterEnderecoPeloCpf(principal.getName()));
    }
}
