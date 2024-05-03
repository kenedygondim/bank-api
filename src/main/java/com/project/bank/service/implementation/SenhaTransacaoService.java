package com.project.bank.service.implementation;

import com.project.bank.entity.dto.SenhaTransacaoDto;
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

        Conta conta = contaRepository.findById(senhaTransacao.getContaId()).orElseThrow(
                () -> new RegistroNaoEncontradoException("conta", senhaTransacao.getContaId())
        );

        if(conta.getSenhaTransacao() != null)
            throw new BusinessException("A conta já possui uma senha de transação cadastrada.");

        if(!senhaTransacao.getSenha().equals(senhaTransacao.getConfirmacaoSenha()))
            throw new BusinessException("As senhas não conferem.");

        if(senhaTransacao.getSenha().length() != 6)
            throw new BusinessException("A senha deve conter 6 dígitos.");

        verificaDigitosSenha(senhaTransacao.getSenha());

        SenhaTransacao objConstruido =
                SenhaTransacao.builder()
                        .senha(senhaTransacao.getSenha())
                        .conta(conta)
                        .build();

        return senhaTransacaoRepository.save(objConstruido);
    }

    @Override
    public SenhaTransacao atualizarSenhaTransacao(SenhaTransacaoPutForm senhaTransacao)
    {
        verificaDigitosSenha(senhaTransacao.getNovaSenha());

        Conta conta = contaRepository.findById(senhaTransacao.getContaId()).orElseThrow(
                () -> new RegistroNaoEncontradoException("conta", senhaTransacao.getContaId())
        );

        if(conta.getSenhaTransacao() == null) //se não houver senha cadastrada, criará uma com o valor de "novaSenha"
        {
            SenhaTransacaoPostForm senhaTransacaoBuiler = SenhaTransacaoPostForm.builder()
                    .senha(senhaTransacao.getNovaSenha())
                    .contaId(senhaTransacao.getContaId())
                    .build();

            cadastrarSenhaTransacao(senhaTransacaoBuiler);
        }
        if(!senhaTransacao.getSenhaAtual().equals(conta.getSenhaTransacao().getSenha()))
            throw new BusinessException("A senha atual não confere.");

        if(senhaTransacao.getSenhaAtual().equals(senhaTransacao.getNovaSenha()))
            throw new BusinessException("A nova senha não pode ser igual a senha atual.");

        conta.getSenhaTransacao().setSenha(senhaTransacao.getNovaSenha());

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
