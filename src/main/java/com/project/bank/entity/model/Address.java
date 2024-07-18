package com.project.bank.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_user_address")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "postal_code",nullable = false, length = 9)
    private String postalCode;

    @Column(nullable = false, length = 2)
    private String state;

    @Column(nullable = false, length = 20)
    private String city;

    @Column(nullable = false, length = 20)
    private String neighborhood;

    @Column(nullable = false, length = 50)
    private String street;

    @Column(nullable = false, length = 10)
    private String number;

    @Column(nullable = true, length = 10)
    private String complement;

    @OneToOne
    @JoinColumn(name = "user_personal_info_id", nullable = false)
    @JsonIgnore
    private UserPersonalInfo userPersonalInfo;
}
