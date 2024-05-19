package com.project.bank.entity.form;

import jakarta.validation.constraints.NotBlank;
import javax.validation.constraints.*;

public record AuthForm
        (
                @NotBlank(message = "O campo de CPF não pode estar vazio.") //o valor não pode ser nulo e nem vazio
                @Size(min = 11, max = 11, message = "O cpf deve ter exatamente 11 caracteres, sem pontos ou traços") //o tamanho do cpf deve ser igual a 11
                String login,
                @NotBlank(message = "O campo de senha não pode estar vazio.")
                @Size(min = 8, max = 8, message = "A senha deve ter exatamente 8 caracteres.") //o tamanho da senha deve ser entre 8 e 16
                String senha
        ) { }
