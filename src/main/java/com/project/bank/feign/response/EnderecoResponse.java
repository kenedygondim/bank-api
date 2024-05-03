package com.project.bank.feign.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EnderecoResponse
{
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
}
