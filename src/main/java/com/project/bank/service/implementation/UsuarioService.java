package com.project.bank.service.implementation;

import com.project.bank.entity.model.Usuario;
import com.project.bank.repository.UsuarioRepository;
import com.project.bank.service.repository.UsuarioServiceRep;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioServiceRep
{
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> obterUsuarios()
    {
        return usuarioRepository.findAll();
    }
    @Override
    public Usuario obterUsuario(String cpf)
    {
        return usuarioRepository.findByCpf(cpf);
    }
}
