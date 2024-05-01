package com.project.bank.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_senhas_transacoes")
@AllArgsConstructor @NoArgsConstructor
@Builder
@Getter
@Setter
public class SenhaTransacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 6) //Por enquanto vai ser uma senha simples de 6 digitos
    private String senha;

    @OneToOne
    @JoinColumn(name = "conta_id", nullable = false)
    @JsonIgnore
    private Conta conta;
}
