package com.project.bank.entity.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class TransferenciaForm
{
    private long contaOrigemId;
    private String chavePix;
    private double valor;
    private String senhaTransacao;
}
