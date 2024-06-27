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
    @Query("SELECT p FROM ChavePix p JOIN p.conta c JOIN c.usuario u WHERE u.cpf = :cpf")
    List<ChavePix> findAllByUsuarioCpf(String cpf);
    Optional<ChavePix> findByChave(String chave);
}
