package com.project.bank.entity.form;

public record TransferenciaForm
        (
                long contaOrigemId,
                String chavePix,
                double valor,
                String senhaTransacao
        ) { }