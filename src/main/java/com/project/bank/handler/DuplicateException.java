package com.project.bank.handler;

public class DuplicateException extends BusinessException
{
    public DuplicateException(String info)
    {
        super("Já existe um %s cadastrado no sistema.", info);
    }
}
