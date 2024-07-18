package com.project.bank.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_user_personal_info")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserPersonalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "first_name", nullable = false, length = 25)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 25)
    private String lastName;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(name = "date_of_birth", nullable = false, length = 10)
    private String dateOfBirth;

    @Setter
    @Column(nullable = false, length = 30, unique = true)
    private String email;

    @Setter
    @Column(name = "phone_number", nullable = false, length = 11, unique = true)
    private String phoneNumber;

    @OneToOne(mappedBy = "userPersonalInfo", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private Address address;
}
