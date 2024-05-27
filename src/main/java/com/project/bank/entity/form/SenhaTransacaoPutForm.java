package com.project.bank.entity.form;

import jakarta.validation.constraints.NotBlank;

public record SenhaTransacaoPutForm
        (
                @NotBlank(message = "O campo de senha atual não pode estar vazio.")
                String senhaAtual,
                @NotBlank(message = "O campo de nova senha não pode estar vazio.")
                String novaSenha
        ) { }