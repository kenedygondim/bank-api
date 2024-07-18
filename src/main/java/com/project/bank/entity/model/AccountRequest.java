package com.project.bank.entity.model;

import com.project.bank.enumeration.RequestStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_account_requests")
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

    @Builder.Default
    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private RequestStatusEnum requestStatusEnum = RequestStatusEnum.PENDING;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "account_request_date_time")
    private LocalDateTime accountRequestDateTime;
}
