package com.project.bank.service.implementation;

import com.project.bank.email.EmailService;
import com.project.bank.entity.model.*;
import com.project.bank.enumeration.AccountStatusEnum;
import com.project.bank.enumeration.AccountTypeEnum;
import com.project.bank.enumeration.RoleEnum;
import com.project.bank.handler.NotFoundException;
import com.project.bank.repository.AccountRepository;
import com.project.bank.service.repository.AccountRepositoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AccountService implements AccountRepositoryService {
    private final AccountRepository accountRepository;
    private final  AccountRequestService accountRequestService;
    private final EmailService emailService;
    private final AddressService addressService;
    private final ClientService clientService;
    private final AccountAccessService accountAccessService;

    @Autowired
    public AccountService(AccountRepository accountRepository, AccountRequestService accountRequestService, EmailService emailService, AddressService addressService, ClientService clientService, AccountAccessService accountAccessService) {
        this.accountRepository = accountRepository;
        this.accountRequestService = accountRequestService;
        this.emailService = emailService;
        this.addressService = addressService;
        this.clientService = clientService;
        this.accountAccessService = accountAccessService;
    }

    @Override
    @Transactional
    public String approveAllAccounts() {
        accountRequestService.getAccountRequests().forEach(accountRequest -> {
            this.approveAccount(accountRequest.getId());
        });
        return "Contas aprovadas com sucesso!";
    }

    @Override
    @Transactional
    public String approveAccount(String requestId) {
        AccountRequest accountRequest = accountRequestService.getAccountRequest(requestId);
        Address address = addressService.saveAddress(createAddressObject(accountRequest));
        Client client = clientService.saveClient(createUserPersonalInfoObject(accountRequest, address));
        AccountAccess accountAccess = accountAccessService.saveAccountAccess(createAccountAccessObject(accountRequest));
        Account account = createUserBankInfoObject(accountRequest, client, accountAccess);
        emailService.sendEmail(emailService.generateApprovedAccountEmail(account));
        this.saveAccount(account);
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
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account getClientAccount(String cpf) {
        return accountRepository.findAccountByCpf(cpf).orElseThrow(
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

    private static Client createUserPersonalInfoObject(AccountRequest accountRequest, Address address) {
        return Client.builder()
                .firstName(accountRequest.getFirstName())
                .lastName(accountRequest.getLastName())
                .cpf(accountRequest.getCpf())
                .dateOfBirth(accountRequest.getDateOfBirth())
                .email(accountRequest.getEmail())
                .phoneNumber(accountRequest.getPhoneNumber())
                .address(address)
                .build();
    }

    private static Address createAddressObject(AccountRequest accountRequest) {
        return Address.builder()
                .postalCode(accountRequest.getPostalCode())
                .stateAbbr(accountRequest.getStateAbbr())
                .city(accountRequest.getCity())
                .neighborhood(accountRequest.getNeighborhood())
                .street(accountRequest.getStreet())
                .houseNumber(accountRequest.getHouseNumber())
                .complement(accountRequest.getComplement())
                .build();
    }

    private static AccountAccess createAccountAccessObject(AccountRequest accountRequest) {
        return AccountAccess.builder()
                .login(accountRequest.getCpf())
                .password(accountRequest.getPassword())
                .role(RoleEnum.USER)
                .build();
    }

    private static Account createUserBankInfoObject(AccountRequest accountRequest, Client client, AccountAccess accountAccess) {
        return Account.builder()
                .branchNumber("0001")
                .accountNumber(generateAccountNumber())
                .accountType(AccountTypeEnum.CHECKING)
                .accountStatus(AccountStatusEnum.ACTIVE)
                .client(client)
                .accountAccess(accountAccess)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
