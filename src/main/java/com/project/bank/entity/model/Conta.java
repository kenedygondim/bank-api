package com.project.bank.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.bank.enumeration.StatusConta;
import com.project.bank.enumeration.TipoConta;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_contas")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tipo_conta", nullable = false)
    private TipoConta tipoConta;

    @Column(name = "status_conta", nullable = false)
    private StatusConta statusConta;

    @Column(nullable = false, length = 4)
    @Builder.Default
    private String agencia = "0001";

    @Column(name = "num_conta", nullable = false, length = 8)
    private String numConta;

    @Column(nullable = false)
    @Setter
    @Builder.Default
    private Double saldo = 0.0;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @OneToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @OneToOne(mappedBy = "conta")
    private SenhaTransacao senhaTransacao;
}
