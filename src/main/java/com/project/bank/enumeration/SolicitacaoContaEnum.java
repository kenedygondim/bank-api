package com.project.bank.enumeration;

public enum SolicitacaoContaEnum
{
    PENDENTE("Pendente"),
    RECUSADA("Recusada"),
    APROVADA("Aprovada");

    private final String solicitacao;

    SolicitacaoContaEnum(String solicitacao)
    {
        this.solicitacao = solicitacao;
    }

    public String getSolicitacao()
    {
        return solicitacao;
    }

}
