package com.project.bank.entity.dto;
import com.project.bank.enumeration.TipoChave;
import lombok.Builder;

@Builder
public class ChavePixDto {
    private String id;
    private TipoChave tipoChave;
    private String chave;
}
