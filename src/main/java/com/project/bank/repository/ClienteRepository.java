package com.project.bank.repository;

import com.project.bank.entity.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String>
{
    UserDetails findByCpf(String cpf);
}
