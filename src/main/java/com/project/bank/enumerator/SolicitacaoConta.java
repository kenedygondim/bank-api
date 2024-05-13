package com.project.bank.enumerator;

public enum SolicitacaoConta
{
    PENDENTE("Pendente"),
    RECUSADA("Recusada"),
    APROVADA("Aprovada");

    private final String solicitacao;

    SolicitacaoConta(String solicitacao)
    {
        this.solicitacao = solicitacao;
    }

    public String getSolicitacao()
    {
        return solicitacao;
    }

}
