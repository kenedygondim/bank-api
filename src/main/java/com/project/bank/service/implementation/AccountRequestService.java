package com.project.bank.service.implementation;

import com.project.bank.email.EmailService;
import com.project.bank.entity.dto.AccountRequestDto;
import com.project.bank.entity.model.AccountAccess;
import com.project.bank.entity.model.AccountRequest;
import com.project.bank.enumeration.RoleEnum;
import com.project.bank.feign.AddressFeign;
import com.project.bank.feign.response.AddressResponse;
import com.project.bank.handler.BusinessException;
import com.project.bank.handler.DuplicateException;
import com.project.bank.handler.NotFoundException;
import com.project.bank.repository.AccountRequestRepository;
import com.project.bank.service.repository.AccountRequestRepositoryService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class AccountRequestService implements AccountRequestRepositoryService {
    private final EmailService emailService;
    private final AddressFeign addressFeign;
    private final AccountRequestRepository accountRequestRepository;
    private final ClientService clientService;
    private final AccountAccessService accountAccessService;

    @Autowired
    public AccountRequestService(EmailService emailService, AddressFeign addressFeign, AccountRequestRepository accountRequestRepository, ClientService clientService, AccountAccessService accountAccessService) {
        this.emailService = emailService;
        this.addressFeign = addressFeign;
        this.accountRequestRepository = accountRequestRepository;
        this.clientService = clientService;
        this.accountAccessService = accountAccessService;
    }

    @Override
    @Transactional
    public AccountRequest requestAccount(AccountRequestDto accountRequestDto) {
        if (this.existsByCpf(accountRequestDto.cpf()))
            throw new BusinessException("Solicitação de conta já realizada.");
        validateUserAge(accountRequestDto.dateOfBirth());
        verifyExistingFields(accountRequestDto);
        Optional<AddressResponse> addressResponse = addressFeign.getAddressByPostalCode(accountRequestDto.postalCode());
        AddressResponse addressResponseValidated = validatePostalCode(addressResponse);
        AccountRequest accountRequest = this.saveAccountRequest(createAccountRequestObject(accountRequestDto, addressResponseValidated));
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
        accountAccessService.saveAccountAccess(accountAccess);
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

    @Override
    public Boolean existsByCpf(String cpf) {
        return accountRequestRepository.existsByCpf(cpf);
    }

    @Override
    public AccountRequest saveAccountRequest(AccountRequest accountRequest) {
        return accountRequestRepository.save(accountRequest);
    }

    private static AccountRequest createAccountRequestObject(AccountRequestDto accountRequestDto, AddressResponse addressResponse) {
        return AccountRequest.builder()
                .firstName(accountRequestDto.firstName())
                .lastName(accountRequestDto.lastName())
                .cpf(accountRequestDto.cpf())
                .dateOfBirth(accountRequestDto.dateOfBirth())
                .email(accountRequestDto.email())
                .phoneNumber(accountRequestDto.phoneNumber().replaceAll("[^0-9]", ""))
                .password(new BCryptPasswordEncoder().encode(accountRequestDto.password()))
                .postalCode(accountRequestDto.postalCode())
                .city(addressResponse.getLocalidade())
                .stateAbbr(addressResponse.getUf())
                .neighborhood(addressResponse.getBairro())
                .street(addressResponse.getLogradouro())
                .houseNumber(accountRequestDto.houseNumber())
                .complement(accountRequestDto.complement())
                .createdAt(LocalDateTime.now())
                .build();
    }

    private static AddressResponse validatePostalCode(Optional<AddressResponse> addressResponse) {
        if (addressResponse.isPresent())
            return new ModelMapper().map(addressResponse, AddressResponse.class);
        throw new BusinessException("CEP não encontrado");
    }

    private void verifyExistingFields(AccountRequestDto accountRequestDto) {
        if (clientService.existsByCpf(accountRequestDto.cpf()))
            throw new DuplicateException("CPF");
        if (clientService.existsByEmail(accountRequestDto.email()))
            throw new DuplicateException("email");
        if (clientService.existsByPhoneNumber(accountRequestDto.phoneNumber()))
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
