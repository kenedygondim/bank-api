package com.project.bank.entity.dto;

import com.project.bank.enumeration.AccountStatusEnum;
import com.project.bank.enumeration.AccountTypeEnum;
import lombok.Builder;

@Builder
public record BankAccountInfoDto
        (
              String id,
              AccountTypeEnum accountTypeEnum,
              AccountStatusEnum accountStatusEnum,
              String branchNumber,
              String accountNumber
        )
{}
