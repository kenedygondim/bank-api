package com.project.bank.repository;

import com.project.bank.entity.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>
{
    UserDetails findByCpf(String cpf);
    Usuario findFirstByCpf(String cpf);
}
