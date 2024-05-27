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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class SenhaTransacaoService implements SenhaTransacaoServiceRep
{
    private final SenhaTransacaoRepository senhaTransacaoRepository;
    private final ContaRepository contaRepository;
    @Override
    public SenhaTransacao cadastrarSenhaTransacao(SenhaTransacaoPostForm senhaTransacao, String cpf)
    {
        Conta conta = contaRepository.findContaByUsuarioCpf(cpf);
        if(conta.getSenhaTransacao() != null)
            throw new BusinessException("A conta já possui uma senha de transação cadastrada.");
        if(!senhaTransacao.senha().equals(senhaTransacao.confirmacaoSenha()))
            throw new BusinessException("As senhas não conferem.");
        SenhaTransacao objConstruido =
                SenhaTransacao.builder()
                        .senha(new BCryptPasswordEncoder().encode(senhaTransacao.senha()))
                        .conta(conta)
                        .build();
        return senhaTransacaoRepository.save(objConstruido);
    }

    @Override
    public SenhaTransacao atualizarSenhaTransacao(SenhaTransacaoPutForm senhaTransacao, String cpf)
    {
        Conta conta = contaRepository.findContaByUsuarioCpf(cpf);
        if(!new BCryptPasswordEncoder().matches(senhaTransacao.senhaAtual(), conta.getSenhaTransacao().getSenha()))
            throw new BusinessException("A senha atual não confere.");
        if(new BCryptPasswordEncoder().matches(senhaTransacao.novaSenha(), conta.getSenhaTransacao().getSenha()))
            throw new BusinessException("A nova senha não pode ser igual a senha atual.");
        conta.getSenhaTransacao().setSenha(senhaTransacao.novaSenha());
        return senhaTransacaoRepository.save(conta.getSenhaTransacao());
    }
}
