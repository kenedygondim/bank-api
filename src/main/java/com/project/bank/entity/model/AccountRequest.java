package com.project.bank.entity.model;

import com.project.bank.enumeration.RequestStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_account_request")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AccountRequest
{
    //adicionar coluna de data da solicitação

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "first_name", nullable = false, length = 25)
    private String firstName;

    @Column(name="last_name", nullable = false, length = 25)
    private String lastName;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(name = "date_of_birth", nullable = false, length = 10)
    private String dateOfBirth;

    @Column(nullable = false, length = 30, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 11, unique = true)
    private String phoneNumber;

    @Column(name = "postal_code",nullable = false, length = 9)
    private String postalCode;

    @Column(name = "state_abbr", nullable = false, length = 2)
    private String stateAbbr;

    @Column(nullable = false, length = 20)
    private String city;

    @Column(nullable = false, length = 20)
    private String neighborhood;

    @Column(nullable = false, length = 50)
    private String street;

    @Column(name = "house_number", nullable = false, length = 10)
    private String houseNumber;

    @Column(nullable = true, length = 10)
    private String complement;

    @Column(name = "password", nullable = false)
    private String password;

    @Builder.Default
    @Column(name = "request_status", nullable = true)
    @Enumerated(EnumType.STRING)
    private RequestStatusEnum requestStatus = RequestStatusEnum.PENDING;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
