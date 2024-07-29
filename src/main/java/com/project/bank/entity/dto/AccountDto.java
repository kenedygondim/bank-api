package com.project.bank.entity.dto;

import com.project.bank.enumeration.AccountStatusEnum;
import com.project.bank.enumeration.AccountTypeEnum;
import lombok.Builder;

@Builder
public record AccountDto
        (
              String id,
              AccountTypeEnum accountTypeEnum,
              AccountStatusEnum accountStatusEnum,
              String branchNumber,
              String accountNumber
        )
{}
