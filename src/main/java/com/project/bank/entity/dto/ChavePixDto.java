package com.project.bank.entity.dto;

import com.project.bank.enumerator.TipoChave;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class ChavePixDto {
    private long id;
    private String tipoChave;
    private String chave;
}
