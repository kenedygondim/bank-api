package com.project.bank.enumeration;

public enum TipoConta {
    CORRENTE("CORRENTE"),
    POUPANCA("POUPANCA");

    private final String tipoConta;

    TipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public String getCodigo() {
        return this.tipoConta;
    }
}
