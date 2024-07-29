package com.project.bank.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.bank.enumeration.AccountTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import com.project.bank.enumeration.AccountStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_account")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "account_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountTypeEnum accountType;

    @Column(name = "account_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatusEnum accountStatus;

    @Column(name = "branch_number" ,nullable = false, length = 4)
    @Builder.Default
    private String branchNumber = "0001";

    @Column(name = "account_number", nullable = false, length = 8)
    private String accountNumber;

    @Column(nullable = false)
    @Setter
    @Builder.Default
    private BigDecimal balance = BigDecimal.valueOf(100.00);

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "client_id", nullable = false)
    @JsonIgnore
    private Client client;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_access_id", nullable = false)
    @JsonIgnore
    private AccountAccess accountAccess;

    @OneToOne(mappedBy = "account")
    @JsonIgnore
    private TransactionPassword transactionPassword;
}
