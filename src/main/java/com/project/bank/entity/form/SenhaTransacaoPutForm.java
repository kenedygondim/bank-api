package com.project.bank.entity.form;

public record SenhaTransacaoPutForm
        (
                String senhaAtual,
                String novaSenha,
                long contaId
        ) { }