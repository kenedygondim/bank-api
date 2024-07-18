package com.project.bank.repository;

import com.project.bank.entity.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String>
{
    @Query("SELECT t FROM Transaction t WHERE t.receiver.id = :id OR t.sender.id = :id")
    List<Transaction> findAllBySenderOrReceiverId(String id);
}
