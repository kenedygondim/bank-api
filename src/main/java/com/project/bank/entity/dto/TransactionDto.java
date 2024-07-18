package com.project.bank.entity.dto;

import com.project.bank.entity.model.BankAccountInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransactionDto
        (
                String id,
                BankAccountInfoDto sender,
                BankAccountInfo receiver,
                @NotBlank(message = "Insira a chave pix.")
                String keyValue,
                @NotBlank(message = "Selecione o valor.")
                @Min(value = 1, message = "O valor da transferência deve ser maior que R$ 0,99.")
                @Max(value = 100000, message = "O valor da transferência deve ser menor que R$ 100.000,00.")
                BigDecimal value,
                @NotBlank(message = "Insira a password da transação.")
                String transactionPassword,
                LocalDateTime transactionDateTime
        ) { }
