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
public class AccountAccessController
{
    private final AuthenticationManager authManager;
    private final AccountAccessRepository accountAccessRepository;
    private final TokenService tokenService;

    @Autowired
    public AccountAccessController(AuthenticationManager authManager, AccountAccessRepository accountAccessRepository, TokenService tokenService) {
        this.authManager = authManager;
        this.accountAccessRepository = accountAccessRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody @Valid AccessAccountDto form)
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
