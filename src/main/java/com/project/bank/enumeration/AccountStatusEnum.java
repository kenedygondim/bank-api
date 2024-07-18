package com.project.bank.enumeration;

public enum AccountStatusEnum
{
    ACTIVE("ATIVA"),
    INACTIVE("INATIVA"),
    BLOCKED("BLOQUEADA");

    private final String status;

    AccountStatusEnum(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
