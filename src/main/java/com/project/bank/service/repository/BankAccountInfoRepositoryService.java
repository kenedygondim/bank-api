package com.project.bank.service.repository;

import com.project.bank.entity.model.BankAccountInfo;
import org.springframework.stereotype.Service;

@Service
public interface BankAccountInfoRepositoryService {
    String approveAccount(String id);

    String disapproveAccount(String id);

    BankAccountInfo getUserAccount(String cpf);
}
