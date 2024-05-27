package com.project.bank.entity.form;

import com.project.bank.enumeration.TipoChave;
import jakarta.validation.constraints.NotNull;

public record ChavePixForm
        (
            @NotNull(message = "Selecione o tipo de chave que deseja criar!")
            TipoChave tipoChave
        ) { }

