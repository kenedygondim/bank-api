package com.project.bank.service.repository;

import com.project.bank.entity.dto.SenhaTransacaoDto;
import com.project.bank.entity.model.SenhaTransacao;
import org.springframework.stereotype.Service;

@Service
public interface SenhaTransacaoServiceRep
{
    SenhaTransacao cadastrarSenhaTransacao(SenhaTransacao senhaTransacao);
    SenhaTransacao atualizarSenhaTransacao(SenhaTransacaoDto senhaTransacao, String senhaAtual);
}
