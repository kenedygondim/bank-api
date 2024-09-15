package com.project.bank.resource.controller;

import com.project.bank.entity.dto.AccountRequestDto;
import com.project.bank.entity.model.AccountRequest;
import com.project.bank.service.implementation.AccountRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank/accountRequest")
public class AccountRequestController
{
    private final AccountRequestService accountRequestService;

    @Autowired
    public AccountRequestController (AccountRequestService accountRequestService){
        this.accountRequestService = accountRequestService;
    }

    @PostMapping
    public ResponseEntity<AccountRequest> requestAccount(@RequestBody @Valid AccountRequestDto accountRequestDto)
    {
        return ResponseEntity.ok(accountRequestService.requestAccount(accountRequestDto));
    }
    @GetMapping("/requests/all")
    public ResponseEntity<List<AccountRequest>> getRequests()
    {
        return ResponseEntity.ok(accountRequestService.getAccountRequests());
    }
    @PostMapping("/adminAccount")
    public void createAdminAccount()
    {
        accountRequestService.createAdminAccount();
    }
}
