package com.project.bank.service.repository;

import com.project.bank.entity.dto.ContaDto;
import com.project.bank.entity.model.Conta;
import org.springframework.stereotype.Service;

@Service
public interface ContaServiceRep
{
    String aprovarConta(String id);
    String reprovarConta(String id);
}
