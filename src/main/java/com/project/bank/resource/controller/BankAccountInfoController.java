package com.project.bank.resource.controller;
import com.project.bank.service.implementation.BankAccountInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Validated
@RestController
@RequestMapping("/bank")
@RequiredArgsConstructor
public class BankAccountInfoController
{
    @Autowired
    private BankAccountInfoService bankAccountInfoService;

    @PostMapping("/approveAccount/{id}")
    public ResponseEntity<String> approveAccount(@PathVariable("id") String id)
    {
        return ResponseEntity.ok(bankAccountInfoService.approveAccount(id));
    }
    @DeleteMapping("/disapproveAccount/{id}")
    public ResponseEntity<String> disapproveAccount(@PathVariable("id") String id)
    {
        return ResponseEntity.ok(bankAccountInfoService.disapproveAccount(id));
    }
}
