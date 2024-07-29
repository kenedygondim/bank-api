package com.project.bank.repository;

import com.project.bank.entity.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>
{
    @Query("SELECT c FROM Account c INNER JOIN c.client ON c.client.cpf = :cpf ")
    Optional<Account> findAccountByCpf(String cpf);
}
