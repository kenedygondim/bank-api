package com.project.bank.repository;

import com.project.bank.entity.model.TransactionPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionPasswordRepository extends JpaRepository<TransactionPassword, String> { }
