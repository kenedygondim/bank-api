package com.project.bank.service.implementation;
import com.project.bank.entity.dto.ContaDto;
import com.project.bank.entity.dto.TransferenciaDto;
import com.project.bank.entity.form.TransferenciaForm;
import com.project.bank.entity.model.ChavePix;
import com.project.bank.entity.model.Conta;
import com.project.bank.entity.model.Transferencia;
import com.project.bank.enumeration.StatusConta;
import com.project.bank.enumeration.TipoConta;
import com.project.bank.handler.BusinessException;
import com.project.bank.handler.RegistroNaoEncontradoException;
import com.project.bank.repository.ChavePixRepository;
import com.project.bank.repository.ContaRepository;
import com.project.bank.repository.TransferenciaRepository;
import com.project.bank.service.repository.TransferenciaServiceRep;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Service
public class TransferenciaService implements TransferenciaServiceRep
{
    private final TransferenciaRepository transferenciaRepository;
    private final ContaRepository contaRepository;
    private final ChavePixRepository chavePixRepository;
    @Override
    @Transactional
    public TransferenciaDto realizarTransferencia(TransferenciaForm transferencia, String cpf)
    {
        ChavePix chave = chavePixRepository.findByChave(transferencia.chavePix()).orElseThrow(
                () -> new RegistroNaoEncontradoException("chave PIX", transferencia.chavePix())
        );
        Conta contaOrigem = contaRepository.findContaByUsuarioCpf(cpf);
        if (contaOrigem == null)
            throw new RegistroNaoEncontradoException("conta", cpf);
        if(contaOrigem.getSenhaTransacao() == null)
            throw new BusinessException("Cadastre uma senha para transações.");
        if(transferencia.valor() > contaOrigem.getSaldo())
            throw new BusinessException("Saldo insuficiente");
        if(chave.getConta().equals(contaOrigem))
            throw new BusinessException("Não é possível transferir para a própria conta");;
        if(contaOrigem.getTipoConta().equals(TipoConta.POUPANCA))
            throw new BusinessException("Não é possível transferir a partir de uma conta-poupança");
        StatusConta statusContaDestino = chave.getConta().getStatusConta();
        if(statusContaDestino.equals(StatusConta.BLOQUEADA) || statusContaDestino.equals(StatusConta.INATIVA))
            throw new BusinessException("Conta destino bloqueada ou inativa no momento.");
        if(!new BCryptPasswordEncoder().matches(transferencia.senhaTransacao(), contaOrigem.getSenhaTransacao().getSenha()))
            throw new BusinessException("Senha incorreta.");
       Transferencia transferenciaBuilder =
               Transferencia.builder()
                       .remetente(contaOrigem)
                       .destinatario(chave.getConta())
                       .valor(transferencia.valor())
                       .dataTransferencia(LocalDateTime.now())
                       .build();
       contaOrigem.setSaldo(contaOrigem.getSaldo() - transferencia.valor());
       chave.getConta().setSaldo(chave.getConta().getSaldo() + transferencia.valor());
       transferenciaRepository.save(transferenciaBuilder);
       return converteTransferenciaDto(transferenciaBuilder);
    }

    @Override
    public TransferenciaDto obterTransferencia(long id)
    {
        Transferencia transferencia = transferenciaRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("chave PIX", id)
        );
        return converteTransferenciaDto(transferencia);
    }
    @Override
    public List<TransferenciaDto> obterTransferenciasCliente(String cpf)
    {
        Conta conta = contaRepository.findContaByUsuarioCpf(cpf);
        if (conta == null)
            throw new RegistroNaoEncontradoException("conta", cpf);
        return converteListaTransferenciaDto(transferenciaRepository.findAllByRemetenteIdOrDestinatarioId(conta.getId()));
    }

    public List<TransferenciaDto> converteListaTransferenciaDto(List<Transferencia> transferencias)
    {
        List<TransferenciaDto> transferenciasDto = new ArrayList<>();
        for (Transferencia transferencia : transferencias)
            transferenciasDto.add(converteTransferenciaDto(transferencia));
        return transferenciasDto;
    }
    private TransferenciaDto converteTransferenciaDto(Transferencia transferencia)
    {
        return TransferenciaDto.builder()
                .id(transferencia.getId())
                .remetente(converteContaDto(transferencia.getRemetente()))
                .destinatario(converteContaDto(transferencia.getDestinatario()))
                .valor(transferencia.getValor())
                .dataTransferencia(transferencia.getDataTransferencia())
                .build();
    }
    private ContaDto converteContaDto(Conta conta)
    {
        return ContaDto.builder()
                .id(conta.getId())
                .agencia(conta.getAgencia())
                .tipoConta(conta.getTipoConta())
                .statusConta(conta.getStatusConta())
                .conta(conta.getNumConta())
                .build();
    }
}
