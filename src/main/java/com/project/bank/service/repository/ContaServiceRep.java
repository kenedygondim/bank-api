package com.project.bank.service.repository;

import com.project.bank.entity.dto.ContaDto;
import com.project.bank.entity.model.Conta;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ContaServiceRep
{
    Conta cadastrarConta(Conta conta);
    Conta obterConta(long id);
    Conta atualizarConta(ContaDto conta);
    String excluirConta(long id);
}
