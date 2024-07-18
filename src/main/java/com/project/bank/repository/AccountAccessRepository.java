package com.project.bank.repository;

import com.project.bank.entity.model.AccountAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountAccessRepository extends JpaRepository<AccountAccess, String>
{
    UserDetails findByLogin(String login);
    AccountAccess findFirstByLogin(String login);
}