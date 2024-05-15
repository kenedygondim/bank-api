package com.project.bank.entity.dto;

import com.project.bank.enumeration.TipoChave;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChavePixDto {
    private long id;
    private TipoChave tipoChave;
    private String chave;
}
