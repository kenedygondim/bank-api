package com.project.bank.resource.controller;
import com.project.bank.entity.form.ChavePixForm;
import com.project.bank.entity.model.ChavePix;
import com.project.bank.service.implementation.ChavePixService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/bank/chavePix")
@RequiredArgsConstructor
public class ChavePixController
{
    private final ChavePixService chavePixService;
    @GetMapping("/{id}")
    public ResponseEntity<List<ChavePix>> listarChavesPixConta(@PathVariable("id")long id)
    {
        return ResponseEntity.ok(chavePixService.listarChavesPixConta(id));
    }
    @PostMapping
    public ResponseEntity<ChavePix> cadastrarChavePix(@RequestBody @Valid ChavePixForm chavePix)
    {
        return ResponseEntity.ok(chavePixService.cadastrarChavePix(chavePix));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirChavePix(@PathVariable("id") long id){
        chavePixService.excluirChavePix(id);
        return ResponseEntity.ok("Chave PIX excluída");
    }
}
