package com.project.bank.entity.form;

import com.project.bank.enumerator.UserRole;

public record RegisterForm
        (
                 String primeiroNome,
                 String sobrenome,
                 String cpf,
                 String dataNascimento,
                 String email,
                 String numeroTelefone,
                 String senha
        ) { }
