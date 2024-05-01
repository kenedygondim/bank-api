package com.project.bank.entity.form;

import com.project.bank.enumerator.TipoChave;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChavePixForm {
    private TipoChave TipoChave;
    private long ContaId;
}
