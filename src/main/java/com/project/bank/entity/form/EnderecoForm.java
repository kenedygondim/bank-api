package com.project.bank.entity.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoForm
{
    private String cep;
    private String numero;
    private String complemento;
    private long clienteId;
}
