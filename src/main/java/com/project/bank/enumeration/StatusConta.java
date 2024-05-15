package com.project.bank.enumeration;

public enum StatusConta {
    ATIVA("ATIVA"),
    INATIVA("INATIVA"),
    BLOQUEADA("BLOQUEADA");

    private String statusConta;

   StatusConta(String statusConta){
        this.statusConta = statusConta;
    }

    public String getStatusConta(){
        return statusConta;
    }
}
