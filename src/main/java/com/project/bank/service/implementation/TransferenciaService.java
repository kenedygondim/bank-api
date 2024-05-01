package com.project.bank.service.implementation;

import com.project.bank.entity.dto.ContaDto;
import com.project.bank.entity.dto.TransferenciaDto;
import com.project.bank.entity.form.TransferenciaForm;
import com.project.bank.entity.model.ChavePix;
import com.project.bank.entity.model.Conta;
import com.project.bank.entity.model.Transferencia;
import com.project.bank.enumerator.StatusConta;
import com.project.bank.enumerator.TipoConta;
import com.project.bank.handler.BusinessException;
import com.project.bank.handler.RegistroNaoEncontradoException;
import com.project.bank.repository.ChavePixRepository;
import com.project.bank.repository.ContaRepository;
import com.project.bank.repository.TransferenciaRepository;
import com.project.bank.service.repository.TransferenciaServiceRep;
import lombok.RequiredArgsConstructor;
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
    public TransferenciaDto realizarTransferencia(TransferenciaForm transferencia)
    {
        ChavePix chave = chavePixRepository.findByChave(transferencia.getChavePix()).orElseThrow(
                () -> new RegistroNaoEncontradoException("chave PIX", transferencia.getChavePix())
        );
        Conta contaOrigem = contaRepository.findById(transferencia.getContaOrigemId()).orElseThrow(
                () -> new RegistroNaoEncontradoException("conta", transferencia.getContaOrigemId())
        );

        if(transferencia.getValor() <= 0.01)
            throw new BusinessException("Transações PIX devem ser acima de 1 centavo.");

        if(transferencia.getValor() > contaOrigem.getSaldo())
            throw new BusinessException("Saldo insuficiente");

        if(chave.getConta().equals(contaOrigem))
            throw new BusinessException("Não é possível transferir para a própria conta");

        TipoConta tipoContaOrigem = contaOrigem.getTipoConta();
        if(tipoContaOrigem.equals(TipoConta.POUPANCA))
            throw new BusinessException("Não é possível transferir de uma conta-poupança");

        StatusConta statusContaDestino = chave.getConta().getStatusConta();
        if(statusContaDestino.equals(StatusConta.BLOQUEADA) || statusContaDestino.equals(StatusConta.INATIVA))
            throw new BusinessException("Conta destino bloqueada no momento.");

        if(!chave.getConta().getSenhaTransacao().getSenha().equals(transferencia.getSenhaTransacao()))
            throw new BusinessException("Senha inválida!");

       Transferencia objConstruido =
               Transferencia.builder()
                       .remetente(contaOrigem)
                       .destinatario(chave.getConta())
                       .valor(transferencia.getValor())
                       .dataTransferencia(LocalDateTime.now())
                       .build();

       //Alterar posteriormente
       contaOrigem.setSaldo(contaOrigem.getSaldo() - transferencia.getValor());
       chave.getConta().setSaldo(chave.getConta().getSaldo() + transferencia.getValor());

       transferenciaRepository.save(objConstruido);
       contaRepository.save(contaOrigem);
       contaRepository.save(chave.getConta());

       return converteTransferenciaDto(objConstruido);
    }

    @Override
    public TransferenciaDto obterTransferencia(long id) {
        Transferencia transferencia = transferenciaRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("chave PIX", id)
        );

        return converteTransferenciaDto(transferencia);
    }

    @Override
    public List<TransferenciaDto> obterTransferenciasCliente(long id) {
        List<Transferencia> transfs = transferenciaRepository.findAllByRemetenteIdOrDestinatarioId(id);
        return converteListaTransferenciaDto(transfs);
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
                .conta(conta.getConta())
                .build();
    }
}
