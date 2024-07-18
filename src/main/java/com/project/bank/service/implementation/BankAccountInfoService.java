package com.project.bank.service.implementation;

import com.project.bank.email.EmailService;
import com.project.bank.entity.model.AccountAccess;
import com.project.bank.entity.model.AccountRequest;
import com.project.bank.entity.model.BankAccountInfo;
import com.project.bank.entity.model.UserPersonalInfo;
import com.project.bank.enumeration.AccountStatusEnum;
import com.project.bank.enumeration.AccountTypeEnum;
import com.project.bank.enumeration.RoleEnum;
import com.project.bank.handler.NotFoundException;
import com.project.bank.repository.BankAccountInfoRepository;
import com.project.bank.service.repository.BankAccountInfoRepositoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class BankAccountInfoService implements BankAccountInfoRepositoryService {
    @Autowired
    private BankAccountInfoRepository bankAccountInfoRepository;
    @Autowired
    private AccountRequestService accountRequestService;
    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public String approveAccount(String requestId) {
        AccountRequest accountRequest = accountRequestService.getAccountRequest(requestId);
        BankAccountInfo bankAccountInfo = createUserBankInfoObject(accountRequest);
        emailService.sendEmail(emailService.generateApprovedAccountEmail(bankAccountInfo));
        bankAccountInfoRepository.save(bankAccountInfo);
        accountRequestService.deleteAccountRequest(requestId);
        return "Conta aprovada com sucesso!";
    }

    @Override
    public String disapproveAccount(String requestId) {
        AccountRequest accountRequest = accountRequestService.getAccountRequest(requestId);
        accountRequestService.deleteAccountRequest(requestId);
        emailService.sendEmail(emailService.generateDisapprovedAccountEmail(accountRequest));
        return "Conta reprovada com sucesso!";
    }

    @Override
    public BankAccountInfo getUserAccount(String cpf) {
        return bankAccountInfoRepository.findUserByCpf(cpf).orElseThrow(
                () -> new NotFoundException("usuário", cpf)
        );
    }

    private static String generateAccountNumber() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 7; i++)
            if (i == 5) stringBuilder.append("-");
            else stringBuilder.append(random.nextInt(10));
        return stringBuilder.toString();
    }

    private static UserPersonalInfo createUserPersonalInfoObject(AccountRequest accountRequest) {
        return UserPersonalInfo.builder()
                .firstName(accountRequest.getFirstName())
                .lastName(accountRequest.getLastName())
                .cpf(accountRequest.getCpf())
                .dateOfBirth(accountRequest.getDateOfBirth())
                .email(accountRequest.getEmail())
                .phoneNumber(accountRequest.getPhoneNumber())
                .build();
    }

    private static AccountAccess createAccountAccessObject(AccountRequest accountRequest) {
        return AccountAccess.builder()
                .login(accountRequest.getCpf())
                .password(accountRequest.getPassword())
                .role(RoleEnum.USER)
                .build();
    }

    private static BankAccountInfo createUserBankInfoObject(AccountRequest accountRequest) {
        return BankAccountInfo.builder()
                .branchNumber("0001")
                .accountNumber(generateAccountNumber())
                .accountType(AccountTypeEnum.CHECKING)
                .accountStatus(AccountStatusEnum.ACTIVE)
                .balance(BigDecimal.valueOf(0.0))
                .userPersonalInfo(createUserPersonalInfoObject(accountRequest))
                .accountAccess(createAccountAccessObject(accountRequest))
                .creationDateTime(LocalDateTime.now())
                .build();
    }
}
