package com.project.bank.service.implementation;

import com.project.bank.entity.dto.ContaDto;
import com.project.bank.entity.dto.TransferenciaDto;
import com.project.bank.entity.model.Conta;
import com.project.bank.entity.model.Transferencia;
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

    @Override
    public Transferencia realizarTransferencia(long idContaOrigem, long idContaDestino, double valor) {
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
        List<TransferenciaDto> transfsDto = new ArrayList<>();

        for(int i = 0; i < transfs.size(); i++)
        {
            transfsDto.add(converteTransferenciaDto(transfs.get(i)));
        }

        return transfsDto;
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
