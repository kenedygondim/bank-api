package com.project.bank.entity.dto;

import com.project.bank.entity.model.Conta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TransferenciaDto
{
    private long id;
    private ContaDto remetente;
    private ContaDto destinatario;
    private double valor;
    private LocalDateTime dataTransferencia;
}
