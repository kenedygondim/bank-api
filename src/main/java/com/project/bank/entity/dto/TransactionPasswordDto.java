package com.project.bank.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record TransactionPasswordDto
        (
            String id,
            @NotBlank(message = "O campo de password não pode estar vazio.")
            @Pattern(regexp = "^[0-9]{6}$", message = "O campo deve ser composto apenas por 6 números.")
            String transactionPassword
        ) {}

