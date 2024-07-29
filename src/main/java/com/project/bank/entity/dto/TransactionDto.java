package com.project.bank.entity.dto;

import com.project.bank.entity.model.Account;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransactionDto
        (
                String id,
                AccountDto sender,
                Account receiver,
                @NotBlank(message = "Insira a chave pix.")
                String keyValue,
                @NotNull(message = "Selecione o valor.")
                @Min(value = 1, message = "O valor da transferência deve ser maior que R$ 0,99.")
                @Max(value = 100000, message = "O valor da transferência deve ser menor que R$ 100.000,00.")
                BigDecimal value,
                @NotBlank(message = "Insira a password da transação.")
                String transactionPassword,
                LocalDateTime createdAt
        ) { }
