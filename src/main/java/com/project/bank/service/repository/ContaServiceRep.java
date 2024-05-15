package com.project.bank.service.repository;

import com.project.bank.entity.dto.ContaDto;
import com.project.bank.entity.model.Conta;
import org.springframework.stereotype.Service;

@Service
public interface ContaServiceRep
{
    void aprovarConta(String id);
    void reprovarConta(String id);
    Conta atualizarConta(ContaDto conta);
    String excluirConta(long id);
}
