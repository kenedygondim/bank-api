package com.project.bank.enumerator;

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
