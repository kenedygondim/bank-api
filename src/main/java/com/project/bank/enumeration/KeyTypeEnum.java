package com.project.bank.enumeration;

public enum KeyTypeEnum
{
    CPF("CPF"),
    PHONE_NUMBER("NUMERO DE TELEFONE"),
    EMAIL("EMAIL"),
    RANDOM("ALEATORIA");

    private final String type;

    KeyTypeEnum(String type) {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
}
