package com.project.bank.entity.dto;

import com.project.bank.enumeration.StatusConta;
import com.project.bank.enumeration.TipoConta;
import lombok.Builder;

@Builder
public class ContaDto
{
    private String id;
    private TipoConta tipoConta;
    private StatusConta statusConta;
    private String agencia;
    private String conta;
}
