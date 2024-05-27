package com.project.bank.repository;

import com.project.bank.entity.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>
{
    Usuario findByCpf(String cpf);
    Boolean existsByCpf(String cpf);
    Boolean existsByEmail(String email);
    Boolean existsByNumeroTelefone(String numeroTelefone);
}
