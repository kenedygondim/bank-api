package com.project.bank.repository;

import com.project.bank.entity.model.SenhaTransacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SenhaTransacaoRepository extends JpaRepository<SenhaTransacao, Long> { }
