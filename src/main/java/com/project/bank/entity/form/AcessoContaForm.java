package com.project.bank.entity.form;

import jakarta.validation.constraints.NotBlank;

public record AcessoContaForm
        (
                @NotBlank(message = "O campo de login não pode estar vazio.")
                String login,
                @NotBlank(message = "O campo de senha não pode estar vazio.")
                String senha
        ) {
}
