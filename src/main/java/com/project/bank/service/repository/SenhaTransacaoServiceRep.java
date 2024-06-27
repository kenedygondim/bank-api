package com.project.bank.service.repository;

import com.project.bank.entity.form.SenhaTransacaoPostForm;
import com.project.bank.entity.form.SenhaTransacaoPutForm;
import com.project.bank.entity.model.SenhaTransacao;
import org.springframework.stereotype.Service;

@Service
public interface SenhaTransacaoServiceRep
{
    String cadastrarSenhaTransacao(SenhaTransacaoPostForm senhaTransacao, String cpf);
    SenhaTransacao atualizarSenhaTransacao(SenhaTransacaoPutForm senhaTransacao, String cpf);
}
