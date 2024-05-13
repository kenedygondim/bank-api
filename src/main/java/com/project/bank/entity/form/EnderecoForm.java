package com.project.bank.entity.form;

public record EnderecoForm
        (
             String cep,
             String numero,
             String complemento,
             String clienteId
        ) { }