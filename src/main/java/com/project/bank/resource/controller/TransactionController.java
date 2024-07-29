package com.project.bank.resource.controller;

import com.project.bank.entity.dto.TransactionDto;
import com.project.bank.entity.model.Transaction;
import com.project.bank.service.implementation.TransactionsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/bank/transaction")
@RequiredArgsConstructor
public class TransactionController
{
    @Autowired
    private TransactionsService transactionsService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody @Valid TransactionDto transactionDto, Principal principal)
    {
        return ResponseEntity.ok(transactionsService.createTransaction(transactionDto, principal.getName()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getTransaction(@PathVariable("id") String transactionId)
    {
        return ResponseEntity.ok(transactionsService.getTransaction(transactionId));
    }
    @GetMapping("/all")
    public ResponseEntity<List<TransactionDto>> getAllTransactions(Principal principal)
    {
        return ResponseEntity.ok(transactionsService.getTransactions(principal.getName()));
    }
}
