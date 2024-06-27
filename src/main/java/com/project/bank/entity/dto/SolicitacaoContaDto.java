package com.project.bank.entity.dto;
import lombok.Builder;

@Builder
public class SolicitacaoContaDto
{
    private String id;
    private String primeiroNome;
    private String sobrenome;
    private String cpf;
    private String dataNascimento;
    private String email;
    private String numeroTelefone;
}
