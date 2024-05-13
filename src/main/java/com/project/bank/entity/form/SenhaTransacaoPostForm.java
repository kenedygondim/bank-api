package com.project.bank.entity.form;

public record SenhaTransacaoPostForm
        (
                String senha,
                String confirmacaoSenha,
                long contaId
        ) { }
