package com.project.bank.resource.controller;

import com.project.bank.entity.dto.AddressDto;
import com.project.bank.entity.model.Address;
import com.project.bank.service.implementation.AddressService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping("/bank/address")
public class AddressController
{
    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody @Valid AddressDto addressDto, Principal principal)
    {
        return ResponseEntity.ok(addressService.createAddress(addressDto, principal.getName()));
    }
    @GetMapping
    public ResponseEntity<Address> getAddressByCpf(Principal principal)
    {
        return ResponseEntity.ok(addressService.getAddressByCpf(principal.getName()));
    }
}
