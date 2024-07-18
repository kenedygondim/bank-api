package com.project.bank.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_user_transaction_password")
@AllArgsConstructor @NoArgsConstructor
@Builder
@Getter
@Setter
public class TransactionPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="transaction_password",nullable = false, length = 150) //Por enquanto vai ser uma password simples de 6 digitos
    private String transactionPassword;

    @OneToOne (cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_bank_info_id",  nullable = false)
    @JsonIgnore
    private BankAccountInfo bankAccountInfo;
}
