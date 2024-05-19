package com.project.bank.repository;

import com.project.bank.entity.model.SolicitacaoConta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitacaoContaRepository extends JpaRepository<SolicitacaoConta, String>{
    Boolean existsByCpf(String cpf);
}