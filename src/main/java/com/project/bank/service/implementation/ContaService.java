package com.project.bank.service.implementation;

import com.project.bank.entity.dto.ContaDto;
import com.project.bank.entity.model.Conta;
import com.project.bank.service.repository.ContaServiceRep;
import org.springframework.stereotype.Service;

@Service
public class ContaService implements ContaServiceRep {
    @Override
    public Conta cadastrarConta(Conta conta) {
        return null;
    }

    @Override
    public Conta obterConta(long id) {
        return null;
    }

    @Override
    public Conta atualizarConta(ContaDto conta) {
        return null;
    }

    @Override
    public String excluirConta(long id) {
        return null;
    }
}
