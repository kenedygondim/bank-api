package com.project.bank.resource.controller;

import com.project.bank.entity.model.Cliente;
import com.project.bank.service.implementation.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank/clientes")
@RequiredArgsConstructor
public class ClienteController
{
    private final ClienteService clienteService;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obterCliente(@PathVariable("id") long id)
    {
        return ResponseEntity.ok(clienteService.obterCliente(id));
    }

    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente)
    {
        return ResponseEntity.ok(clienteService.cadastrarCliente(cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirCliente(@PathVariable("id") long id)
    {
        return ResponseEntity.ok(clienteService.excluirCliente(id));
    }
}
