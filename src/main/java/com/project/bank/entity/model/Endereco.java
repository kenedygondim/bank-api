package com.project.bank.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_enderecos")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 9)
    private String cep;

    @Column(nullable = false, length = 2)
    private String uf;

    @Column(nullable = false, length = 20)
    private String cidade;

    @Column(nullable = false, length = 20)
    private String bairro;

    @Column(nullable = false, length = 50)
    private String logradouro;

    @Column(nullable = false, length = 10)
    private String numero;

    @Column(nullable = true, length = 10)
    private String complemento;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;
}
