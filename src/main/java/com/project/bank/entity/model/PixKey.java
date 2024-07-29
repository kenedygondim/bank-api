package com.project.bank.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.bank.enumeration.KeyTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_pix_key")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PixKey {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "key_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private KeyTypeEnum keyType;

    @Column(name = "key_value", nullable = false, length = 40, unique = true)
    private String keyValue;

    @ManyToOne
    @JoinColumn(name = "account_id",nullable = false)
    @JsonIgnore
    private Account account;
}
