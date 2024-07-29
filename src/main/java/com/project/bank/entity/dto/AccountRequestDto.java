package com.project.bank.entity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.br.CPF;

@Builder
public record AccountRequestDto(
        String id,
        @NotBlank(message = "O campo de primeiro nome não pode estar vazio.")
        @Pattern(regexp = "^[A-Z][a-z]+$", message = "O campo deve começar com uma letra maiúscula e conter mais de uma letra.")
        String firstName,

        @NotBlank(message = "O campo de sobrenome não pode estar vazio.")
        @Pattern(regexp = "^[A-Za-z][a-z]+( [A-Za-z][a-z]*)*$", message = "O campo deve começar com uma letra, conter mais de uma letra e permitir espaços após duas letras ou mais.")
        String lastName,

        @NotBlank(message = "O campo de cpf não pode estar vazio.")
        String cpf,

        @NotBlank(message = "O campo de data de nascimento não pode estar vazio.")
        String dateOfBirth,

        @Email
        @NotBlank(message = "O campo de email não pode estar vazio.")
        String email,

        @NotBlank(message = "O campo de número de telefone não pode estar vazio.")
        @Pattern(regexp = "^\\([1-9]{2}\\)\\s9[0-9]{4}-[0-9]{4}$", message = "O número de telefone deve seguir o padrão (XX) 9XXXX-XXXX.")
        String phoneNumber,

        @Size(min = 8, max = 12, message = "A password de acesso deve ter entre 8 e 12 caracteres.")
        String password,

        @NotBlank(message = "O cep não pode estar vazio.")
        String postalCode,

        @NotBlank(message = "O número da casa não pode estar vazio.")
        String houseNumber,

        String complement
)
{
}
