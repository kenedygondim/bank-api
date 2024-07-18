package com.project.bank.repository;

import com.project.bank.entity.model.BankAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountInfoRepository extends JpaRepository<BankAccountInfo, Long>
{
    @Query("SELECT c FROM BankAccountInfo c INNER JOIN c.userPersonalInfo ON c.userPersonalInfo.cpf = :cpf ")
    Optional<BankAccountInfo> findUserByCpf(String cpf);
}
