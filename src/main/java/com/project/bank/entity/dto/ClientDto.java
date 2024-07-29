package com.project.bank.entity.dto;

import lombok.Builder;

@Builder
public record ClientDto
        (
               String id,
               String fullName,
               String cpf,
               String dateOfBirth,
               String email,
               String phoneNumber
        )
{
}

