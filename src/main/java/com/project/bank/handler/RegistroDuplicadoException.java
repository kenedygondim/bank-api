package com.project.bank.handler;

public class RegistroDuplicadoException extends BusinessException
{
    public RegistroDuplicadoException(String info)
    {
        super("Já existe um %s cadastrado no sistema.", info);
    }

    public RegistroDuplicadoException(String tipo, String valor)
    {
        super("Registro de %s com valor %s não encontrado.", tipo, valor);
    }
}
