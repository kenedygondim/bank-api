package com.project.bank.service.repository;

import com.project.bank.entity.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UsuarioServiceRep
{
    List<Usuario> obterUsuarios();
}
