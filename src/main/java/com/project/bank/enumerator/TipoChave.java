package com.project.bank.enumerator;

public enum TipoChave {
    CPF("CPF"),
    NUMERO_TELEFONE("TELEFONE"),
    EMAIL("EMAIL"),
    ALEATORIA("ALEATORIA");

    private final String tipoChave;

    TipoChave(String tipoChave) {
        this.tipoChave = tipoChave;
    }

    public String getCodigo() {
        return tipoChave;
    }
}
