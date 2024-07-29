package com.project.bank.service.implementation;

import com.project.bank.entity.model.AccountAccess;
import com.project.bank.repository.AccountAccessRepository;
import com.project.bank.service.repository.AccountAccessRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountAccessService implements AccountAccessRepositoryService {
    private final AccountAccessRepository accountAccessRepository;

    @Autowired
    public AccountAccessService(AccountAccessRepository accountAccessRepository) {
        this.accountAccessRepository = accountAccessRepository;
    }

    @Override
    public AccountAccess saveAccountAccess(AccountAccess accountAccess) {
        return accountAccessRepository.save(accountAccess);
    }
}
