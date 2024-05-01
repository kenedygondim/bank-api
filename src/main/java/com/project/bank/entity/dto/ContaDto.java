package com.project.bank.entity.dto;

import com.project.bank.enumerator.StatusConta;
import com.project.bank.enumerator.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ContaDto
{
    private long id;
    private TipoConta tipoConta;
    private StatusConta statusConta;
    private String agencia;
    private String conta;
}
