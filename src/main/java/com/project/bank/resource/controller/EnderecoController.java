package com.project.bank.resource.controller;

import com.project.bank.entity.form.EnderecoForm;
import com.project.bank.entity.model.Endereco;
import com.project.bank.service.implementation.EnderecoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/bank/endereco")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<Endereco> cadastrarEndereco(@RequestBody EnderecoForm endereco)
    {
        return ResponseEntity.ok(enderecoService.cadastrarEndereco(endereco));
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<Endereco> obterEnderecoPeloClienteId(@PathVariable long clienteId)
    {
        return ResponseEntity.ok(enderecoService.obterEnderecoPeloClienteId(clienteId));
    }
}
