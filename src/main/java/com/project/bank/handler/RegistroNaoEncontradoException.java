package com.project.bank.handler;

public class RegistroNaoEncontradoException extends BusinessException
{
    public RegistroNaoEncontradoException(String tipo, long id)
    {
        super("Registro de %s com id %s não encontrado.", tipo, id);
    }

    public RegistroNaoEncontradoException(String tipo, String valor)
    {
        super("Registro de %s com valor %s não encontrado.", tipo, valor);
    }
}
