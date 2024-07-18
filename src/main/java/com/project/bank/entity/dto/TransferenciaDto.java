package com.project.bank.entity.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class TransferenciaDto
{
    private String id;
    private ContaDto remetente;
    private ContaDto destinatario;
    private double valor;
    private LocalDateTime dataTransferencia;
}
