package com.project.bank.entity.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
//Alterar para receber todos os dados do endereço
@AllArgsConstructor
@Builder
public class EnderecoDto {
    private String cep;
    private String numero;
    private String complemento;

    public EnderecoDto(String cep, String numero)
    {
        this.cep = cep;
        this.numero = numero;
    }
}
