package com.project.bank.service.repository;

import com.project.bank.entity.dto.AccountRequestDto;
import com.project.bank.entity.model.AccountRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountRequestRepositoryService {
    AccountRequest requestAccount(AccountRequestDto accountRequestDto);

    AccountRequest getAccountRequest(String requestId);

    List<AccountRequest> getAccountRequests();

    void deleteAccountRequest(String solicitacaoId);

    void createAdminAccount();
}
