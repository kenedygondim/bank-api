package com.project.bank.resource.controller;
import com.project.bank.service.implementation.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Validated
@RestController
@RequestMapping("/bank/conta")
@RequiredArgsConstructor
public class ContaController
{
    private final ContaService contaService;
    @PostMapping("/aprovarConta/{id}")
    public ResponseEntity<String> aprovarConta(@PathVariable("id") String id)
    {
        return ResponseEntity.ok(contaService.aprovarConta(id));
    }
    @DeleteMapping("/reprovarConta/{id}")
    public ResponseEntity<String> reprovarConta(@PathVariable("id") String id)
    {
        return ResponseEntity.ok(contaService.reprovarConta(id));
    }
}
