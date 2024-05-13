package com.project.bank.service.implementation;

import com.project.bank.entity.form.SenhaTransacaoPostForm;
import com.project.bank.entity.form.SenhaTransacaoPutForm;
import com.project.bank.entity.model.Conta;
import com.project.bank.entity.model.SenhaTransacao;
import com.project.bank.handler.BusinessException;
import com.project.bank.handler.RegistroNaoEncontradoException;
import com.project.bank.repository.ContaRepository;
import com.project.bank.repository.SenhaTransacaoRepository;
import com.project.bank.service.repository.SenhaTransacaoServiceRep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SenhaTransacaoService implements SenhaTransacaoServiceRep {

    private final SenhaTransacaoRepository senhaTransacaoRepository;
    private final ContaRepository contaRepository;

    @Override
    public SenhaTransacao cadastrarSenhaTransacao(SenhaTransacaoPostForm senhaTransacao) {

        Conta conta = contaRepository.findById(senhaTransacao.contaId()).orElseThrow(
                () -> new RegistroNaoEncontradoException("conta", senhaTransacao.contaId())
        );

        if(conta.getSenhaTransacao() != null)
            throw new BusinessException("A conta já possui uma senha de transação cadastrada.");

        if(!senhaTransacao.senha().equals(senhaTransacao.confirmacaoSenha()))
            throw new BusinessException("As senhas não conferem.");

        if(senhaTransacao.senha().length() != 6)
            throw new BusinessException("A senha deve conter 6 dígitos.");

        verificaDigitosSenha(senhaTransacao.senha());

        SenhaTransacao objConstruido =
                SenhaTransacao.builder()
                        .senha(senhaTransacao.senha())
                        .conta(conta)
                        .build();

        return senhaTransacaoRepository.save(objConstruido);
    }

    @Override
    public SenhaTransacao atualizarSenhaTransacao(SenhaTransacaoPutForm senhaTransacao)
    {
        verificaDigitosSenha(senhaTransacao.novaSenha());

        Conta conta = contaRepository.findById(senhaTransacao.contaId()).orElseThrow(
                () -> new RegistroNaoEncontradoException("conta", senhaTransacao.contaId())
        );

        if(!senhaTransacao.senhaAtual().equals(conta.getSenhaTransacao().getSenha()))
            throw new BusinessException("A senha atual não confere.");

        if(senhaTransacao.senhaAtual().equals(senhaTransacao.novaSenha()))
            throw new BusinessException("A nova senha não pode ser igual a senha atual.");

        conta.getSenhaTransacao().setSenha(senhaTransacao.novaSenha());

        return senhaTransacaoRepository.save(conta.getSenhaTransacao());
    }


    private void verificaDigitosSenha(String senha)
    {
        for(int i = 0; i < senha.length(); i++)
        {
            if(!Character.isDigit(senha.charAt(i)))
                throw new BusinessException("A senha devem conter apenas dígitos.");
        }
    }

}
