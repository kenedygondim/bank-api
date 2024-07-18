package com.project.bank.service.repository;

import com.project.bank.entity.dto.TransactionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionRepositoryService {
    TransactionDto createTransaction(TransactionDto transaction, String cpf);

    TransactionDto getTransaction(String id);

    List<TransactionDto> getTransactions(String cpf);
}
