package com.project.bank.entity.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record RegisterForm
        (
                 @NotBlank(message = "O campo de primeiro nome não pode estar vazio.")
                 @Pattern(regexp = "^[A-Z][a-z]+$", message = "O campo deve começar com uma letra maiúscula e conter mais de uma letra.")
                 String primeiroNome,
                 @NotBlank(message = "O campo de sobrenome não pode estar vazio.")
                 @Pattern(regexp = "^[A-Z][a-z]+$", message = "O campo deve começar com uma letra maiúscula e conter mais de uma letra.")
                 String sobrenome,
                 @CPF
                 @NotBlank(message = "O campo de cpf não pode estar vazio.")
                 String cpf,
                 @NotBlank(message = "O campo de data de nascimento não pode estar vazio.")
                 String dataNascimento,
                 @Email
                 @NotBlank(message = "O campo de email não pode estar vazio.")
                 String email,
                 @NotBlank(message = "O campo de número de telefone não pode estar vazio.")
                 @Pattern(regexp = "^\\([1-9]{2}\\)\\s9[0-9]{4}-[0-9]{4}$", message = "O número de telefone deve seguir o padrão (XX) 9XXXX-XXXX.")
                 String numeroTelefone,
                 @Size(min = 8, max = 8, message = "A senha deve ter exatamente 8 caracteres.")
                 String senha
        ) { }
