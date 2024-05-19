package com.project.bank.email;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDto
{
    @NotNull
    private String ownerRef;
    @NotNull
    private String emailFrom;
    @NotNull
    private String emailTo;
    @NotNull
    private String subject;
    @NotNull
    private String body;
}
