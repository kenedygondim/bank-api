package com.project.bank.resource.controller;
import com.project.bank.service.implementation.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Validated
@RestController
@RequestMapping("/bank")
@RequiredArgsConstructor
public class AccountController
{
    @Autowired
    private AccountService accountService;

    @PostMapping("/approveAccount/{id}")
    public ResponseEntity<String> approveAccount(@PathVariable("id") String id)
    {
        return ResponseEntity.ok(accountService.approveAccount(id));
    }

    @PostMapping("/approveAccount/all")
    public ResponseEntity<String> approveAllAccounts()
    {
        return ResponseEntity.ok(accountService.approveAllAccounts());
    }

    @DeleteMapping("/disapproveAccount/{id}")
    public ResponseEntity<String> disapproveAccount(@PathVariable("id") String id)
    {
        return ResponseEntity.ok(accountService.disapproveAccount(id));
    }
}
