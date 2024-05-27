package com.project.bank.entity.form;

import jakarta.validation.constraints.NotBlank;

public record EnderecoForm
        (
             @NotBlank(message = "O cep não pode estar vazio.")
             String cep,
             @NotBlank(message = "O número da casa não pode estar vazio.")
             String numero,
             String complemento
        ) { }