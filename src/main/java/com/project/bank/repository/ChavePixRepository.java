package com.project.bank.repository;

import com.project.bank.entity.model.ChavePix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChavePixRepository extends JpaRepository<ChavePix, Long>
{
    List<ChavePix> findAllByContaId(long id);
}
