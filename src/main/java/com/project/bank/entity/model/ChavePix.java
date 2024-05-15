package com.project.bank.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.bank.enumeration.TipoChave;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_chaves_pix")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ChavePix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tipo_chave", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoChave tipoChave;

    @Column(nullable = false, length = 40, unique = true)
    private String chave;

    @ManyToOne
    @JoinColumn(name = "conta_id",nullable = false)
    @JsonIgnore
    private Conta conta;

}
