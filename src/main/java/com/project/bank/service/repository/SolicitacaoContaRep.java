package com.project.bank.service.repository;

import com.project.bank.entity.form.SolicitacaoContaForm;
import org.springframework.stereotype.Service;

@Service
public interface SolicitacaoContaRep
{
    String solicitarConta(SolicitacaoContaForm formUsuario);

    //void contaAdmin();
}
