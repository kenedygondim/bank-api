package com.project.bank.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_clientes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "primeiro_nome", nullable = false, length = 25)
    private String primeiroNome;

    @Column(nullable = false, length = 25)
    private String sobrenome;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(name = "data_nascimento", nullable = false, length = 10)
    private String dataNascimento;

    @Setter
    @Column(nullable = false, length = 30, unique = true)
    private String email;

    @Setter
    @Column(nullable = false, length = 11, unique = true)
    private String numeroTelefone;


    //permite que a exclusão de um cliente remova registros de endereço, mas não o contrário
    //obs: essa config não criará uma chave estrangeira do lado do Cliente
    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Endereco endereco;
}
