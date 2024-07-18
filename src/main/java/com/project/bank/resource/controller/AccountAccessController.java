package com.project.bank.resource.controller;
import com.project.bank.entity.dto.AccessAccountDto;
import com.project.bank.entity.model.AccountAccess;
import com.project.bank.handler.BusinessException;
import com.project.bank.repository.AccountAccessRepository;
import com.project.bank.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bank/auth")
@RequiredArgsConstructor
public class AccountAccessController
{
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private AccountAccessRepository accountAccessRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AccessAccountDto form)
    {
        AccountAccess accountAccess = accountAccessRepository.findFirstByLogin(form.login());
        if(accountAccess == null)
            throw new BusinessException("Login inválido.");
        var accessAccountCredentials =  new UsernamePasswordAuthenticationToken(form.login(), form.password());
        var auth = authManager.authenticate(accessAccountCredentials);
        var token = tokenService.generateToken((AccountAccess) auth.getPrincipal());
        return ResponseEntity.ok(token);
    }
}
