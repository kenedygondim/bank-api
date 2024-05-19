package com.project.bank.entity.form;

import com.project.bank.enumeration.TipoChave;
import jakarta.validation.constraints.NotBlank;

public record ChavePixForm
        (
            @NotBlank(message = "Selecione o tipo de chave que deseja criar!")
            TipoChave tipoChave,
            @NotBlank(message = "Selecione a conta na qual a chave está associada.")
            long contaId
        ) { }

