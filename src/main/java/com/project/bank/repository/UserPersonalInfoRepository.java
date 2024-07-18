package com.project.bank.repository;

import com.project.bank.entity.model.UserPersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPersonalInfoRepository extends JpaRepository<UserPersonalInfo, String>
{
    Optional<UserPersonalInfo> findByCpf(String cpf);
    Boolean existsByCpf(String cpf);
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);
}
