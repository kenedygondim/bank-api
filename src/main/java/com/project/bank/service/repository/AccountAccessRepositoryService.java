package com.project.bank.service.repository;

import com.project.bank.entity.model.AccountAccess;
import org.springframework.stereotype.Service;

@Service
public interface AccountAccessRepositoryService
{
    AccountAccess saveAccountAccess(AccountAccess accountAccess);
}
