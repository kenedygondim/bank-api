package com.project.bank.resource.controller;

import com.project.bank.service.implementation.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank/conta")
@RequiredArgsConstructor
public class ContaController
{
    private final ContaService contaService;

    @PostMapping("/aprovarConta/{id}")
    public ResponseEntity<String> aprovarConta(@PathVariable("id") String id)
    {
        contaService.aprovarConta(id);
        return ResponseEntity.ok("Conta aprovada com sucesso!");
    }

    @DeleteMapping("/reprovarConta/{id}")
    public ResponseEntity<String> reprovarConta(@PathVariable("id") String id)
    {
        contaService.reprovarConta(id);
        return ResponseEntity.ok("Conta reprovada com sucesso!");
    }
}
