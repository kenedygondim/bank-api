package com.project.bank.repository;

import com.project.bank.entity.model.AccountRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRequestRepository extends JpaRepository<AccountRequest, String>{
    Boolean existsByCpf(String cpf);
}