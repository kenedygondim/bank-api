package com.project.bank.entity.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AddressDto
        (
        String id,
        @NotBlank(message = "O cep não pode estar vazio.")
        String cep,
        @NotBlank(message = "O número da casa não pode estar vazio.")
        String number,
        String complement
        ) { }
