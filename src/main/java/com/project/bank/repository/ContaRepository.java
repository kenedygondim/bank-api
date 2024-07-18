package com.project.bank.repository;

import com.project.bank.entity.model.ChavePix;
import com.project.bank.entity.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>
{
    @Query("SELECT c FROM Conta c INNER JOIN c.usuario ON c.usuario.cpf = :cpf ")
    Optional<Conta> findContaByUsuarioCpf(String cpf);
}
