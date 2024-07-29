package com.project.bank.service.implementation;

import com.project.bank.entity.dto.TransactionDto;
import com.project.bank.entity.model.Account;
import com.project.bank.entity.model.PixKey;
import com.project.bank.entity.model.Transaction;
import com.project.bank.enumeration.AccountStatusEnum;
import com.project.bank.enumeration.AccountTypeEnum;
import com.project.bank.handler.BusinessException;
import com.project.bank.handler.NotFoundException;
import com.project.bank.repository.TransactionRepository;
import com.project.bank.service.repository.TransactionRepositoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionsService implements TransactionRepositoryService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PixKeyService pixKeyService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public Transaction createTransaction(TransactionDto transaction, String cpf) {
        PixKey pixKey = pixKeyService.getPixKey(transaction.keyValue());
        Account accountSender = accountService.getClientAccount(cpf);
        Account accountReceiver = pixKey.getAccount();
        BigDecimal transactionValue = transaction.value();
        String transactionPassword = transaction.transactionPassword();

        validateTransaction(accountSender, accountReceiver, transactionValue, transactionPassword);
        accountSender.setBalance(accountSender.getBalance().subtract(transactionValue));
        accountReceiver.setBalance(accountReceiver.getBalance().add(transactionValue));
        Transaction userTransactionBuilder = createUserTransactionsObject(accountSender, accountReceiver, transactionValue);
        transactionRepository.save(userTransactionBuilder);
        return userTransactionBuilder;
    }

    @Override
    public TransactionDto getTransaction(String transactionId) {
        Transaction userTransaction = transactionRepository.findById(transactionId).orElseThrow(
                () -> new NotFoundException("chave PIX", transactionId)
        );
        return modelMapper.map(userTransaction, TransactionDto.class);
    }

    @Override
    public List<TransactionDto> getTransactions(String cpf) {
        Account account = accountService.getClientAccount(cpf);
        return convertListUserTransactionsDto(transactionRepository.findAllBySenderOrReceiverId(account.getId()));
    }

    private static void validateTransaction(Account accountSender, Account accountReceiver, BigDecimal value, String passwordTransaction) {
        AccountStatusEnum accountStatusEnumReceiver = accountReceiver.getAccountStatus();

        if (passwordTransaction.isEmpty())
            throw new BusinessException("Cadastre uma password para transações.");
        if (accountSender.getBalance().compareTo(value) < 0)
            throw new BusinessException("Saldo insuficiente");
        if (accountReceiver.equals(accountSender))
            throw new BusinessException("Não é possível transferir para a própria conta");
        if (accountSender.getAccountType().equals(AccountTypeEnum.SAVINGS))
            throw new BusinessException("Não é possível transferir a partir de uma conta-poupança");
        if (accountStatusEnumReceiver.equals(AccountStatusEnum.BLOCKED) || accountStatusEnumReceiver.equals(AccountStatusEnum.INACTIVE))
            throw new BusinessException("Conta destino bloqueada ou inativa no momento.");
        if (!new BCryptPasswordEncoder().matches(passwordTransaction, accountSender.getTransactionPassword().getTransactionPassword()))
            throw new BusinessException("Senha incorreta.");
    }

    private static Transaction createUserTransactionsObject(Account accountSender, Account accountReceiver, BigDecimal value) {
        return Transaction.builder()
                .sender(accountSender)
                .receiver(accountReceiver)
                .value(value)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private List<TransactionDto> convertListUserTransactionsDto(List<Transaction> userTransactions) {
        List<TransactionDto> transferenciasDto = new ArrayList<>();
        for (Transaction userTransaction : userTransactions)
            transferenciasDto.add(modelMapper.map(userTransaction, TransactionDto.class));
        return transferenciasDto;
    }
}
