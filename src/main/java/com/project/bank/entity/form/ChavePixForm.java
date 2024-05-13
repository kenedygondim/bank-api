package com.project.bank.entity.form;

import com.project.bank.enumerator.TipoChave;

public record ChavePixForm
        (
            TipoChave tipoChave,
            long contaId
        ) { }

