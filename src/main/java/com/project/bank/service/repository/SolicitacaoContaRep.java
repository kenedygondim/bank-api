package com.project.bank.service.repository;
import com.project.bank.entity.dto.SolicitacaoContaDto;
import com.project.bank.entity.form.SolicitacaoContaForm;
import com.project.bank.entity.model.SolicitacaoConta;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SolicitacaoContaRep
{
    String solicitarConta(SolicitacaoContaForm formUsuario);
    List<SolicitacaoConta> obterSolicitacoes();
    void contaAdmin();
}
