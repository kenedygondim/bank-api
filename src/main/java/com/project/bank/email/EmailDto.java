package com.project.bank.email;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotNull;

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
