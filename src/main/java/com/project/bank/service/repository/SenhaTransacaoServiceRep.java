package com.project.bank.service.repository;

import com.project.bank.entity.form.SenhaTransacaoForm;
import com.project.bank.entity.model.SenhaTransacao;
import org.springframework.stereotype.Service;

@Service
public interface SenhaTransacaoServiceRep
{
    String cadastrarSenhaTransacao(SenhaTransacaoForm senhaTransacao, String cpf);
    SenhaTransacao atualizarSenhaTransacao(SenhaTransacaoForm senhaTransacao, String cpf);
}
