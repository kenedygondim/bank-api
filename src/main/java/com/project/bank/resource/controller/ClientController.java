package com.project.bank.resource.controller;
import com.project.bank.entity.model.Client;
import com.project.bank.service.implementation.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/bank/client")
@RequiredArgsConstructor
public class ClientController
{
    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<Client> getUser(Principal principal)
    {
        return ResponseEntity.ok(clientService.getUser(principal.getName()));
    }
    @GetMapping("/all")
    public ResponseEntity<List<Client>> getUsers()
    {
        return ResponseEntity.ok(clientService.getClients());
    }
}
