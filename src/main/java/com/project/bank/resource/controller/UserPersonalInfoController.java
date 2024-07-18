package com.project.bank.resource.controller;
import com.project.bank.entity.model.UserPersonalInfo;
import com.project.bank.service.implementation.UserPersonalInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/bank/user")
@RequiredArgsConstructor
public class UserPersonalInfoController
{
    @Autowired
    private UserPersonalInfoService userPersonalInfoService;

    @GetMapping
    public ResponseEntity<UserPersonalInfo> getUser(Principal principal)
    {
        return ResponseEntity.ok(userPersonalInfoService.getUser(principal.getName()));
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserPersonalInfo>> getUsers()
    {
        return ResponseEntity.ok(userPersonalInfoService.getUsers());
    }
}
