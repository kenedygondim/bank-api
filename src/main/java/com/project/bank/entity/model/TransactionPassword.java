package com.project.bank.entity.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_transaction_password")
@AllArgsConstructor @NoArgsConstructor
@Builder
@Getter
@Setter
public class TransactionPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="password",nullable = false, length = 150) //Por enquanto vai ser uma password simples de 6 digitos
    private String transactionPassword;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    Account account;
}
