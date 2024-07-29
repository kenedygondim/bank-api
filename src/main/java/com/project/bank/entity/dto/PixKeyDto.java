package com.project.bank.entity.dto;
import com.project.bank.enumeration.KeyTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PixKeyDto
        (
        String id,
        @NotNull(message = "Selecione o tipo de chave que deseja criar!")
        KeyTypeEnum keyType,
        String keyValue
        ) {}
