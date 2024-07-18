package com.project.bank.service.implementation;

import com.project.bank.email.EmailService;
import com.project.bank.entity.dto.AccountRequestDto;
import com.project.bank.entity.model.AccountAccess;
import com.project.bank.entity.model.AccountRequest;
import com.project.bank.enumeration.RoleEnum;
import com.project.bank.handler.BusinessException;
import com.project.bank.handler.DuplicateException;
import com.project.bank.handler.NotFoundException;
import com.project.bank.repository.AccountAccessRepository;
import com.project.bank.repository.AccountRequestRepository;
import com.project.bank.repository.UserPersonalInfoRepository;
import com.project.bank.service.repository.AccountRequestRepositoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountRequestService implements AccountRequestRepositoryService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private final AccountRequestRepository accountRequestRepository;
    @Autowired
    private final UserPersonalInfoRepository userPersonalInfoRepository;
    @Autowired
    private AccountAccessRepository accountAccessRepository;

    @Override
    @Transactional
    public AccountRequest requestAccount(AccountRequestDto accountRequestDto) {
        if (accountRequestRepository.existsByCpf(accountRequestDto.cpf()))
            throw new BusinessException("Solicitação de conta já realizada.");
        validateUserAge(accountRequestDto.dateOfBirth());
        verifyExistingFields(accountRequestDto);
        AccountRequest accountRequest = accountRequestRepository.save(createAccountRequestObject(accountRequestDto));
        emailService.sendEmail(emailService.generateRequestEmail(accountRequest));
        return accountRequest;
    }

    @Override
    public List<AccountRequest> getAccountRequests() {
        return accountRequestRepository.findAll();
    }

    @Override
    public void createAdminAccount() {
        AccountAccess accountAccess = AccountAccess.builder()
                .login("admin")
                .role(RoleEnum.ADMIN)
                .password(new BCryptPasswordEncoder().encode("admin"))
                .build();
        accountAccessRepository.save(accountAccess);
    }

    @Override
    public AccountRequest getAccountRequest(String requestId) {
        return accountRequestRepository.findById(requestId).orElseThrow(
                () -> new NotFoundException("solicitação de conta", requestId)
        );
    }

    @Override
    public void deleteAccountRequest(String solicitacaoId) {
        accountRequestRepository.deleteById(solicitacaoId);
    }

    private static AccountRequest createAccountRequestObject(AccountRequestDto accountRequestDto) {
        return AccountRequest.builder()
                .firstName(accountRequestDto.firstName())
                .lastName(accountRequestDto.lastName())
                .cpf(accountRequestDto.cpf())
                .dateOfBirth(accountRequestDto.dateOfBirth())
                .email(accountRequestDto.email())
                .phoneNumber(accountRequestDto.phoneNumber().replaceAll("[^0-9]", ""))
                .password(new BCryptPasswordEncoder().encode(accountRequestDto.password()))
                .accountRequestDateTime(LocalDateTime.now())
                .build();
    }

    private void verifyExistingFields(AccountRequestDto accountRequestDto) {
        if (userPersonalInfoRepository.existsByCpf(accountRequestDto.cpf()))
            throw new DuplicateException("CPF");
        if (userPersonalInfoRepository.existsByEmail(accountRequestDto.email()))
            throw new DuplicateException("email");
        if (userPersonalInfoRepository.existsByPhoneNumber(accountRequestDto.phoneNumber()))
            throw new DuplicateException("número de telefone");
    }

    private void validateUserAge(String dateOfBirth) {
        try {
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(dateOfBirth, formatador);
            Period periodo = Period.between(date, LocalDate.now());
            if (periodo.getYears() < 18)
                throw new BusinessException("Usuários devem ser maiores de 18 anos.");
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Data de nascimento inválida", dateOfBirth, 0, e);
        }
    }
}
