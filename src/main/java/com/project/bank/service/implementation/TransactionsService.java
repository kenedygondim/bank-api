package com.project.bank.service.implementation;

import com.project.bank.entity.dto.TransactionDto;
import com.project.bank.entity.model.BankAccountInfo;
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
    private BankAccountInfoService bankAccountInfoService;
    @Autowired
    private PixKeyService pixKeyService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public TransactionDto createTransaction(TransactionDto transaction, String cpf) {
        PixKey pixKey = pixKeyService.getPixKey(transaction.keyValue());
        BankAccountInfo bankAccountInfoSender = bankAccountInfoService.getUserAccount(cpf);
        BankAccountInfo bankAccountInfoReceiver = pixKey.getBankAccountInfo();
        BigDecimal transactionValue = transaction.value();
        String transactionPassword = transaction.transactionPassword();

        validateTransaction(bankAccountInfoSender, bankAccountInfoReceiver, transactionValue, transactionPassword);

        bankAccountInfoSender.setBalance(bankAccountInfoSender.getBalance().subtract(transactionValue));
        bankAccountInfoReceiver.setBalance(bankAccountInfoReceiver.getBalance().add(transactionValue));

        Transaction userTransactionBuilder = createUserTransactionsObject(bankAccountInfoSender, bankAccountInfoReceiver, transactionValue);
        transactionRepository.save(userTransactionBuilder);

        return modelMapper.map(userTransactionBuilder, TransactionDto.class);
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
        BankAccountInfo bankAccountInfo = bankAccountInfoService.getUserAccount(cpf);
        return convertListUserTransactionsDto(transactionRepository.findAllBySenderOrReceiverId(bankAccountInfo.getId()));
    }

    private static void validateTransaction(BankAccountInfo bankAccountInfoSender, BankAccountInfo bankAccountInfoReceiver, BigDecimal value, String passwordTransaction) {
        AccountStatusEnum accountStatusEnumReceiver = bankAccountInfoReceiver.getAccountStatus();

        if (passwordTransaction.isEmpty())
            throw new BusinessException("Cadastre uma password para transações.");
        if (bankAccountInfoSender.getBalance().compareTo(value) < 0)
            throw new BusinessException("Saldo insuficiente");
        if (bankAccountInfoReceiver.equals(bankAccountInfoSender))
            throw new BusinessException("Não é possível transferir para a própria conta");
        ;
        if (bankAccountInfoSender.getAccountType().equals(AccountTypeEnum.SAVINGS))
            throw new BusinessException("Não é possível transferir a partir de uma conta-poupança");
        if (accountStatusEnumReceiver.equals(AccountStatusEnum.BLOCKED) || accountStatusEnumReceiver.equals(AccountStatusEnum.INACTIVE))
            throw new BusinessException("Conta destino bloqueada ou inativa no momento.");
        if (!new BCryptPasswordEncoder().matches(passwordTransaction, bankAccountInfoSender.getTransactionPassword().getTransactionPassword()))
            throw new BusinessException("Senha incorreta.");
    }

    private static Transaction createUserTransactionsObject(BankAccountInfo bankAccountInfoSender, BankAccountInfo bankAccountInfoReceiver, BigDecimal value) {
        return Transaction.builder()
                .sender(bankAccountInfoSender)
                .receiver(bankAccountInfoReceiver)
                .value(value)
                .transactionDateTime(LocalDateTime.now())
                .build();
    }

    private List<TransactionDto> convertListUserTransactionsDto(List<Transaction> userTransactions) {
        List<TransactionDto> transferenciasDto = new ArrayList<>();
        for (Transaction userTransaction : userTransactions)
            transferenciasDto.add(modelMapper.map(userTransaction, TransactionDto.class));
        return transferenciasDto;
    }

    /*private static BankAccountInfoDto convertUserBankInfoDto(BankAccountInfo bankAccountInfo)
    {
        return BankAccountInfoDto.builder()
                .id(bankAccountInfo.getId())
                .branchNumber(bankAccountInfo.getBranchNumber())
                .accountTypeEnum(bankAccountInfo.getAccountType())
                .accountStatusEnum(bankAccountInfo.getAccountStatus())
                .accountNumber(bankAccountInfo.getAccountNumber())
                .build();
    }*/
}
