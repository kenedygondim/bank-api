package com.project.bank.enumeration;

public enum AccountTypeEnum
{
    CHECKING("CORRENTE"),
    SAVINGS("POUPANCA");

    private final String type;

    AccountTypeEnum(String type) {
        this.type = type;
    }

    public String getCodigo() {
        return this.type;
    }
}
