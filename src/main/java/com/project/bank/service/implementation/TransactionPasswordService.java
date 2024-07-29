package com.project.bank.service.implementation;

import com.project.bank.entity.dto.TransactionPasswordDto;
import com.project.bank.entity.model.Account;
import com.project.bank.entity.model.TransactionPassword;
import com.project.bank.handler.BusinessException;
import com.project.bank.repository.TransactionPasswordRepository;
import com.project.bank.service.repository.TransactionPasswordRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionPasswordService implements TransactionPasswordRepositoryService {
    private final TransactionPasswordRepository transactionPasswordRepository;
    private final AccountService accountService;

    @Autowired
    public TransactionPasswordService(TransactionPasswordRepository transactionPasswordRepository, AccountService accountService) {
        this.transactionPasswordRepository = transactionPasswordRepository;
        this.accountService = accountService;
    }

    @Override
    @Transactional
    public String createTransactionPassword(TransactionPasswordDto transactionPasswordDto, String cpf) {
        Account account = accountService.getClientAccount(cpf);
        if (account.getTransactionPassword() != null)
            throw new BusinessException("Já existe uma password de transação cadastrada.");
        transactionPasswordRepository.save(createTransactionPasswordObject(transactionPasswordDto, account));
        return "Senha criada com sucesso!";
    }

    @Override
    public TransactionPassword updateTransactionPassword(TransactionPasswordDto transactionPasswordDto, String cpf) {
        Account account = accountService.getClientAccount(cpf);
        if (new BCryptPasswordEncoder().matches(transactionPasswordDto.transactionPassword(), account.getTransactionPassword().getTransactionPassword()))
            throw new BusinessException("A nova password não pode ser igual a password atual.");
        account.getTransactionPassword().setTransactionPassword(transactionPasswordDto.transactionPassword());
        return this.saveTransactionPassword(account.getTransactionPassword());
    }

    @Override
    public TransactionPassword saveTransactionPassword(TransactionPassword transactionPassword) {
        return transactionPasswordRepository.save(transactionPassword);
    }

    public static TransactionPassword createTransactionPasswordObject(TransactionPasswordDto transactionPasswordDto, Account account) {
        return TransactionPassword.builder()
                .transactionPassword(new BCryptPasswordEncoder().encode(transactionPasswordDto.transactionPassword()))
                .account(account)
                .build();
    }
}
