package com.project.bank.handler;

public class CampoObrigatorioException extends BusinessException {
    public CampoObrigatorioException(String campo) {
        super("Campo obrigatório não informado: %s", campo);
    }
}
