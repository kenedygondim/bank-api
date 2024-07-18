package com.project.bank.resource.controller;

import com.project.bank.entity.dto.PixKeyDto;
import com.project.bank.entity.model.PixKey;
import com.project.bank.service.implementation.PixKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@RestController
@RequestMapping("/bank/pixKey")
@RequiredArgsConstructor
public class PixKeyController
{
    @Autowired
    private PixKeyService pixKeyService;

    @GetMapping("/all")
    public ResponseEntity<List<PixKey>> getPixKeys(Principal principal)
    {
        return ResponseEntity.ok(pixKeyService.getAllPixKeys(principal.getName()));
    }
    @PostMapping
    public ResponseEntity<PixKey> createPixKey(@RequestBody @Valid PixKeyDto pixKey, Principal principal)
    {
        return ResponseEntity.ok(pixKeyService.createPixKey(pixKey, principal.getName()));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePixKey(@RequestBody @Valid PixKeyDto pixKey, Principal principal)
    {
        return ResponseEntity.ok(pixKeyService.deletePixKey(pixKey, principal.getName()));
    }
}
