package com.project.bank.repository;

import com.project.bank.entity.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long>
{
    @Query("SELECT t FROM Transferencia t WHERE t.remetente.id = :id OR t.destinatario.id = :id")
    List<Transferencia> findAllByRemetenteIdOrDestinatarioId(long id);
}
