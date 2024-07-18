package com.project.bank.enumeration;

public enum RequestStatusEnum
{
    PENDING("PENDENTE"),
    REJECTED("REJEITADA"),
    APPROVED("APROVADA");

    private final String status;

    RequestStatusEnum(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
}
