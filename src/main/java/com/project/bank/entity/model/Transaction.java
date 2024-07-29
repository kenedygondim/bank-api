package com.project.bank.entity.model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_transaction")
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
    private Account sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Account receiver;

    @Column(nullable = false)
    private BigDecimal value;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
