package com.project.bank.entity.model;

import com.project.bank.enumeration.SolicitacaoContaEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_solicitacoes_conta")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SolicitacaoConta
{
    //adicionar coluna de data da solicitação

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "primeiro_nome", nullable = false, length = 25)
    private String primeiroNome;

    @Column(nullable = false, length = 25)
    private String sobrenome;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(name = "data_nascimento", nullable = false, length = 10)
    private String dataNascimento;

    @Column(nullable = false, length = 30, unique = true)
    private String email;

    @Column(nullable = false, length = 11, unique = true)
    private String numeroTelefone;

    @Builder.Default
    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private SolicitacaoContaEnum solicitacaoContaEnum = SolicitacaoContaEnum.PENDENTE;

    @Column(name = "senha", nullable = false)
    private String senhaAuth;
}
