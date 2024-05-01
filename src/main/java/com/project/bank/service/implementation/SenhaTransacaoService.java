package com.project.bank.service.implementation;

import com.project.bank.entity.dto.SenhaTransacaoDto;
import com.project.bank.entity.model.SenhaTransacao;
import com.project.bank.service.repository.SenhaTransacaoServiceRep;
import org.springframework.stereotype.Service;

@Service
public class SenhaTransacaoService implements SenhaTransacaoServiceRep {

    @Override
    public SenhaTransacao cadastrarSenhaTransacao(SenhaTransacao senhaTransacao) {
        return null;
    }

    @Override
    public SenhaTransacao atualizarSenhaTransacao(SenhaTransacaoDto senhaTransacao, String senhaAtual) {
        return null;
    }
}
