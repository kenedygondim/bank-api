package com.project.bank.entity.dto;

import lombok.Builder;

@Builder
public class UsuarioDto
{
    private long id;
    private String nomeCompleto;
    private String cpf;
    private String dataNascimento;
    private String email;
    private String numeroTelefone;
}
