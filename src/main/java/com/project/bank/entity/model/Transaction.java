package com.project.bank.entity.model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_user_transactions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private BankAccountInfo sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private BankAccountInfo receiver;

    @Column(nullable = false)
    private BigDecimal value;

    @Column(name = "transaction_date_time", nullable = false)
    private LocalDateTime transactionDateTime;
}
