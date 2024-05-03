package com.project.bank.entity.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SenhaTransacaoPostForm {
    private String senha;
    private String confirmacaoSenha;
    private long contaId;
}
