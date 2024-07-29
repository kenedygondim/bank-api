package com.project.bank.service.repository;

import com.project.bank.entity.model.Account;
import org.springframework.stereotype.Service;

@Service
public interface AccountRepositoryService {
    String approveAccount(String id);
    String approveAllAccounts();
    String disapproveAccount(String id);
    Account saveAccount(Account account);
    Account getClientAccount(String cpf);
}
