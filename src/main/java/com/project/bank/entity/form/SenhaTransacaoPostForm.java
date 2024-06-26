package com.project.bank.entity.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SenhaTransacaoPostForm
        (
                @NotBlank(message = "O campo de senha não pode estar vazio.")
                @Pattern(regexp = "^[0-9]{6}$", message = "O campo deve ser composto apenas por 6 números.")
                String senha,
                @NotBlank(message = "O campo de confirmação de senha não pode estar vazio.")
                String confirmacaoSenha
        ) { }
