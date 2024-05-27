package com.project.bank.resource.controller;
import com.project.bank.entity.form.ChavePixForm;
import com.project.bank.entity.model.ChavePix;
import com.project.bank.service.implementation.ChavePixService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.security.Principal;
import java.util.List;
@RestController
@RequestMapping("/bank/chavePix")
@RequiredArgsConstructor
public class ChavePixController
{
    private final ChavePixService chavePixService;
    @GetMapping
    public ResponseEntity<List<ChavePix>> listarChavesPixConta(Principal principal)
    {
        return ResponseEntity.ok(chavePixService.listarChavesPixConta(principal.getName()));
    }
    @PostMapping
    public ResponseEntity<ChavePix> cadastrarChavePix(@RequestBody @Valid ChavePixForm chavePix, Principal principal)
    {
        return ResponseEntity.ok(chavePixService.cadastrarChavePix(chavePix, principal.getName()));
    }
    @DeleteMapping("/excluir")
    public ResponseEntity<String> excluirChavePix(@RequestBody @Valid ChavePixForm chavePix, Principal principal)
    {
        return ResponseEntity.ok(chavePixService.excluirChavePix(chavePix, principal.getName()));
    }
}
