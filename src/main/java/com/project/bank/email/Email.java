package com.project.bank.email;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Email
{
    private String ownerRef;
    private String emailFrom;
    private String emailTo;
    private String subject;
    private String body;
    private LocalDateTime sendDateEmail;
}
