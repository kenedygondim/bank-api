package com.project.bank.service.implementation;

import com.project.bank.email.EmailDto;
import com.project.bank.email.EmailService;
import com.project.bank.entity.dto.UsuarioDto;
import com.project.bank.entity.form.SolicitacaoContaForm;
import com.project.bank.entity.model.Usuario;
import com.project.bank.handler.BusinessException;
import com.project.bank.handler.RegistroNaoEncontradoException;
import com.project.bank.repository.UsuarioRepository;
import com.project.bank.service.repository.UsuarioServiceRep;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioServiceRep
{
    private final UsuarioRepository usuarioRepository;
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
