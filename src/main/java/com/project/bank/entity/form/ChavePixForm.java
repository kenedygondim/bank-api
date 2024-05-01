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
    @NotNull(message = "Tipo de chave não pode ser nulo")
    private TipoChave TipoChave;
    @NotNull(message = "Conta Id não pode ser nulo")
    private long ContaId;
}
