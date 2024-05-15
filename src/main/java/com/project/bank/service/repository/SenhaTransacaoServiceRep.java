package com.project.bank.service.repository;

import com.project.bank.entity.form.SenhaTransacaoPostForm;
import com.project.bank.entity.form.SenhaTransacaoPutForm;
import com.project.bank.entity.model.SenhaTransacao;
import org.springframework.stereotype.Service;

@Service
public interface SenhaTransacaoServiceRep
{
    SenhaTransacao cadastrarSenhaTransacao(SenhaTransacaoPostForm senhaTransacao);
    SenhaTransacao atualizarSenhaTransacao(SenhaTransacaoPutForm senhaTransacao);
}
