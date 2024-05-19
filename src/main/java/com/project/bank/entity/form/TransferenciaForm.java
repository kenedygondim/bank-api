package com.project.bank.entity.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record TransferenciaForm
        (
                @NotBlank(message = "Selecione a conta de origem.")
                long contaOrigemId,
                @NotBlank(message = "Insira a chave pix.")
                String chavePix,
                @NotBlank(message = "Selecione o valor.")
                @Min(value = 1, message = "O valor da transferência deve ser maior que R$ 0,99.")
                @Max(value = 100000, message = "O valor da transferência deve ser menor que R$ 100.000,00.")
                double valor,
                @NotBlank(message = "Insira a senha da transação.")
                String senhaTransacao
        ) { }