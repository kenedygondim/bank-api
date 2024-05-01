package com.project.bank.repository;

import com.project.bank.entity.model.ChavePix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChavePixRepository extends JpaRepository<ChavePix, Long>
{
    Optional<List<ChavePix>> findAllByContaId(long id);
    Optional<ChavePix> findByChave(String chave);
}
