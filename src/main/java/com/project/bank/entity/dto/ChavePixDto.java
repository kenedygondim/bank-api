package com.project.bank.entity.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChavePixDto {
    private long id;
    private String tipoChave;
    private String chave;
}
