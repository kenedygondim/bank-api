package com.project.bank.service.implementation;

import com.project.bank.entity.dto.TransactionPasswordDto;
import com.project.bank.entity.model.BankAccountInfo;
import com.project.bank.entity.model.TransactionPassword;
import com.project.bank.handler.BusinessException;
import com.project.bank.repository.TransactionPasswordRepository;
import com.project.bank.service.repository.TransactionPasswordRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionPasswordService implements TransactionPasswordRepositoryService {
    @Autowired
    private TransactionPasswordRepository transactionPasswordRepository;
    @Autowired
    private BankAccountInfoService bankAccountInfoService;

    @Override
    public String createTransactionPassword(TransactionPasswordDto transactionPasswordDto, String cpf) {
        BankAccountInfo bankAccountInfo = bankAccountInfoService.getUserAccount(cpf);
        if (bankAccountInfo.getTransactionPassword() != null)
            throw new BusinessException("Já existe uma password de transação cadastrada.");
        transactionPasswordRepository.save(createTransactionPasswordObject(transactionPasswordDto, bankAccountInfo));
        return "Senha criada com sucesso!";
    }

    @Override
    public TransactionPassword updateTransactionPassword(TransactionPasswordDto transactionPasswordDto, String cpf) {
        BankAccountInfo bankAccountInfo = bankAccountInfoService.getUserAccount(cpf);
        if (new BCryptPasswordEncoder().matches(transactionPasswordDto.transactionPassword(), bankAccountInfo.getTransactionPassword().getTransactionPassword()))
            throw new BusinessException("A nova password não pode ser igual a password atual.");
        bankAccountInfo.getTransactionPassword().setTransactionPassword(transactionPasswordDto.transactionPassword());
        return transactionPasswordRepository.save(bankAccountInfo.getTransactionPassword());
    }

    public static TransactionPassword createTransactionPasswordObject(TransactionPasswordDto transactionPasswordDto, BankAccountInfo bankAccountInfo) {
        return TransactionPassword.builder()
                .transactionPassword(new BCryptPasswordEncoder().encode(transactionPasswordDto.transactionPassword()))
                .bankAccountInfo(bankAccountInfo)
                .build();
    }
}
