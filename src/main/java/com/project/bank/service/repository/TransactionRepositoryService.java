package com.project.bank.service.repository;

import com.project.bank.entity.dto.TransactionDto;
import com.project.bank.entity.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionRepositoryService {
    Transaction createTransaction(TransactionDto transaction, String cpf);
    TransactionDto getTransaction(String id);
    List<TransactionDto> getTransactions(String cpf);
}
