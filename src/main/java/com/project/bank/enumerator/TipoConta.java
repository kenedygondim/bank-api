package com.project.bank.enumerator;

public enum TipoConta {
    CORRENTE(0),
    POUPANCA(1);

    private final int codigo;

    TipoConta(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return this.codigo;
    }
}
