package com.project.bank.resource.controller;

import com.project.bank.entity.dto.TransactionPasswordDto;
import com.project.bank.entity.model.TransactionPassword;
import com.project.bank.service.implementation.TransactionPasswordService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
@RestController
@RequestMapping("/bank/transactionPassword")
@AllArgsConstructor
public class TransactionPasswordController
{
    @Autowired
    private TransactionPasswordService transactionPasswordService;
    @PostMapping
    public ResponseEntity<String> createTransactionPassword(@RequestBody @Valid TransactionPasswordDto transactionPasswordDto, Principal principal)
    {
        return ResponseEntity.ok(transactionPasswordService.createTransactionPassword(transactionPasswordDto, principal.getName()));
    }
    @PatchMapping
    public TransactionPassword updateTransactionPassword(@RequestBody @Valid TransactionPasswordDto transactionPasswordDto, Principal principal)
    {
        return transactionPasswordService.updateTransactionPassword(transactionPasswordDto, principal.getName());
    }
}
