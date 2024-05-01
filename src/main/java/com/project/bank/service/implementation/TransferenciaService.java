package com.project.bank.service.implementation;

import com.project.bank.entity.dto.ContaDto;
import com.project.bank.entity.dto.TransferenciaDto;
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
    public Transferencia realizarTransferencia(long idContaOrigem, String chavePix, double valor, String senhaTransacao)
    {
        ChavePix chave = chavePixRepository.findByChave(chavePix).orElseThrow(
                () -> new RegistroNaoEncontradoException("chave PIX", chavePix)
        );
        Conta contaOrigem = contaRepository.findById(idContaOrigem).orElseThrow(
                () -> new RegistroNaoEncontradoException("conta", idContaOrigem)
        );

        if(chave.getConta().equals(contaOrigem))
            throw new BusinessException("Não é possível transferir para a mesma conta");

        TipoConta tipoContaOrigem = contaOrigem.getTipoConta();
        if(tipoContaOrigem.equals(TipoConta.POUPANCA))
            throw new BusinessException("Não é possível transferir de uma conta-poupança");

        StatusConta statusContaDestino = chave.getConta().getStatusConta();
        if(statusContaDestino.equals(StatusConta.BLOQUEADA) || statusContaDestino.equals(StatusConta.INATIVA))
            throw new BusinessException("Conta destino bloqueada no momento.");

        if(!chave.getConta().getSenhaTransacao().getSenha().equals(senhaTransacao))
            throw new BusinessException("Senha inválida!");

        return null;
    }

    @Override
    public TransferenciaDto obterTransferencia(long id) {
        Transferencia transferencia = transferenciaRepository.findById(id).orElse(null);
        if(transferencia == null)
            return null;
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
