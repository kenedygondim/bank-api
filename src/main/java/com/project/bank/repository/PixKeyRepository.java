package com.project.bank.repository;

import com.project.bank.entity.model.PixKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PixKeyRepository extends JpaRepository<PixKey, String>
{
    @Query("SELECT p FROM PixKey p JOIN p.bankAccountInfo c JOIN c.userPersonalInfo u WHERE u.cpf = :cpf")
    List<PixKey> findAllKeysByCpf(String cpf);
    Optional<PixKey> findByKeyValue(String keyValue);
}
