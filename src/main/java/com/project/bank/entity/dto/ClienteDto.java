package com.project.bank.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ClienteDto
{
    private long id;
    private String nomeCompleto;
    private String cpf;
    private String dataNascimento;
    private String email;
    private  String numeroTelefone;
}
