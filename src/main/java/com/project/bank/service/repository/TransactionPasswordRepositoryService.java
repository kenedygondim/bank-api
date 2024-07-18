package com.project.bank.service.repository;

import com.project.bank.entity.dto.TransactionPasswordDto;
import com.project.bank.entity.model.TransactionPassword;
import org.springframework.stereotype.Service;

@Service
public interface TransactionPasswordRepositoryService {
    String createTransactionPassword(TransactionPasswordDto transactionPasswordDto, String cpf);

    TransactionPassword updateTransactionPassword(TransactionPasswordDto transactionPasswordDto, String cpf);
}
