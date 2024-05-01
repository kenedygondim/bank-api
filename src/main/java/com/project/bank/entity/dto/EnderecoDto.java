package com.project.bank.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Builder
@Data
public class EnderecoDto {
    private String cep;
    private String numero;
    private String complemento;

    public EnderecoDto(String cep, String numero) {
        this.cep = cep;
        this.numero = numero;
    }
}
