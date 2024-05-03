package com.project.bank.entity.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SenhaTransacaoPutForm {
    private String senhaAtual;
    private String novaSenha;
    private long contaId;
}
