package com.project.bank.enumerator;

public enum TipoChave {
    CPF(1),
    NUMERO_TELEFONE(2),
    EMAIL(3),
    ALEATORIA(4);

    private final int codigo;

    TipoChave(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return this.codigo;
    }
}
