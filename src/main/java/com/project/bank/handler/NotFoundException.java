package com.project.bank.handler;

public class NotFoundException extends BusinessException
{
    public NotFoundException(String tipo, String valor)
    {
        super("Registro de %s com valor %s não encontrado.", tipo, valor);
    }
}
