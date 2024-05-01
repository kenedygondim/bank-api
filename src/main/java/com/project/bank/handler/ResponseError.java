package com.project.bank.handler;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseError {
    private Date timestamp = new Date();
    private String status;
    private int statusCode;
    private String error;
}
