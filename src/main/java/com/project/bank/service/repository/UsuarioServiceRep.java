package com.project.bank.service.repository;

import com.project.bank.entity.dto.UsuarioDto;
import com.project.bank.entity.form.SolicitacaoContaForm;
import com.project.bank.entity.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsuarioServiceRep
{
    List<Usuario> obterUsuarios();
    Usuario obterUsuario(String id);
}
