package com.project.bank.resource.controller;

import com.project.bank.entity.dto.ClienteDto;
import com.project.bank.entity.form.RegisterForm;
import com.project.bank.entity.model.Cliente;
import com.project.bank.service.implementation.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bank/clientes")
@RequiredArgsConstructor
public class ClienteController
{
    private final ClienteService clienteService;

    @PostMapping("/auth/register")
    public ResponseEntity<String> solicitarConta(@RequestBody @Valid RegisterForm formCliente)
    {
        return ResponseEntity.ok("Conta solicitada com sucesso! Número da solicitação: " +  clienteService.solicitarConta(formCliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obterCliente(@PathVariable("id") String id)
    {
        return ResponseEntity.ok(clienteService.obterCliente(id));
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> obterClientes()
    {
        return ResponseEntity.ok(clienteService.obterClientes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirCliente(@PathVariable("id") String id)
    {
        return ResponseEntity.ok(clienteService.excluirCliente(id));
    }
}
