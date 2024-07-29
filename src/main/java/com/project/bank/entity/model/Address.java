package com.project.bank.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_address")
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

    @Column(name = "state_abbr", nullable = false, length = 2)
    private String stateAbbr;

    @Column(nullable = false, length = 40)
    private String city;

    @Column(nullable = false, length = 40)
    private String neighborhood;

    @Column(nullable = false, length = 40)
    private String street;

    @Column(name="house_number", nullable = false, length = 10)
    private String houseNumber;

    @Column(nullable = true, length = 10)
    private String complement;

    @OneToOne(mappedBy = "address")
    private Client client;
}

