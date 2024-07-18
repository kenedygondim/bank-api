package com.project.bank.service.implementation;

import com.project.bank.entity.form.SenhaTransacaoForm;
import com.project.bank.entity.model.Conta;
import com.project.bank.entity.model.SenhaTransacao;
import com.project.bank.handler.BusinessException;
import com.project.bank.repository.ContaRepository;
import com.project.bank.repository.SenhaTransacaoRepository;
import com.project.bank.service.repository.SenhaTransacaoServiceRep;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class SenhaTransacaoService implements SenhaTransacaoServiceRep
{
    @Autowired
    private SenhaTransacaoRepository senhaTransacaoRepository;
    @Autowired
    private ContaService contaService;

    @Override
    public String cadastrarSenhaTransacao(SenhaTransacaoForm senhaTransacaoForm, String cpf)
    {
        Conta conta = contaService.retornarConta(cpf);
        verificarExistenciaSenha(conta.getSenhaTransacao());
        senhaTransacaoRepository.save(criarObjetoSenhaTransacao(senhaTransacaoForm, conta));
        return "Senha criada com sucesso!";
    }
    @Override
    public SenhaTransacao atualizarSenhaTransacao(SenhaTransacaoForm senhaTransacaoForm, String cpf)
    {
        Conta conta = contaService.retornarConta(cpf);
        verificarNovaSenha(senhaTransacaoForm, conta);
        conta.getSenhaTransacao().setSenha(senhaTransacaoForm.senha());
        return senhaTransacaoRepository.save(conta.getSenhaTransacao());
    }
    public static void verificarExistenciaSenha(SenhaTransacao senhaTransacao)
    {
        if(senhaTransacao != null)
            throw new BusinessException("A conta já possui uma senha de transação cadastrada.");
    }
    public static SenhaTransacao criarObjetoSenhaTransacao(SenhaTransacaoForm senhaTransacaoPostForm, Conta conta)
    {
        return SenhaTransacao.builder()
                        .senha(new BCryptPasswordEncoder().encode(senhaTransacaoPostForm.senha()))
                        .conta(conta)
                        .build();
    }
    public static void verificarNovaSenha(SenhaTransacaoForm senhaTransacaoForm, Conta conta)
    {
        if(new BCryptPasswordEncoder().matches(senhaTransacaoForm.senha(), conta.getSenhaTransacao().getSenha()))
            throw new BusinessException("A nova senha não pode ser igual a senha atual.");
    }
}
