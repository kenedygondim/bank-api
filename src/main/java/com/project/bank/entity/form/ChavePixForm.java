package com.project.bank.entity.form;

import com.project.bank.enumeration.TipoChave;

public record ChavePixForm
        (
            TipoChave tipoChave,
            long contaId
        ) { }

