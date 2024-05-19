package com.project.bank.repository;

import com.project.bank.entity.model.AcessoConta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface AcessoContaRepository extends JpaRepository<AcessoConta, String>
{
    UserDetails findByLogin(String login);
    AcessoConta findFirstByLogin(String login);
}