package com.project.bank.service.repository;

import com.project.bank.entity.dto.TransferenciaDto;
import com.project.bank.entity.model.Transferencia;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransferenciaServiceRep
{
    Transferencia realizarTransferencia(long idContaOrigem, String chavePix, double valor, String senhaTransacao);
    TransferenciaDto obterTransferencia(long id);
    List<TransferenciaDto> obterTransferenciasCliente(long id);
}
