package com.project.bank.repository;

import com.project.bank.entity.model.ChavePix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChavePixRepository extends JpaRepository<ChavePix, Long>
{
    @Query("select cp from ChavePix cp inner join cp.conta on cp.conta.id = :id")
    List<ChavePix> findAllByContaId(Long id);
    Optional<ChavePix> findByChave(String chave);
}
