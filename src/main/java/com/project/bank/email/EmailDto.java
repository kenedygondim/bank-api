package com.project.bank.email;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDto
{
    private String userId;
    private String emailTo;
    private String emailSubject;
    private String emailBody;
}