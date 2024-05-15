package com.project.bank.service.implementation;

import com.project.bank.email.EmailDto;
import com.project.bank.email.EmailService;
import com.project.bank.entity.dto.UsuarioDto;
import com.project.bank.entity.form.RegisterForm;
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
    private final EmailService emailService;

    @Override
    @Transactional
    public String solicitarConta(RegisterForm formUsuario)
    {
        if(retornaIdadeUsuario(formUsuario.dataNascimento()) < 18)
            throw new BusinessException("Usuários devem ser maiores de 18 anos.");

        Usuario usuarioBuilder =
                Usuario.builder()
                        .primeiroNome(formUsuario.primeiroNome())
                        .sobrenome(formUsuario.sobrenome())
                        .cpf(formUsuario.cpf())
                        .dataNascimento(formUsuario.dataNascimento())
                        .email(formUsuario.email())
                        .numeroTelefone(formUsuario.numeroTelefone())
                        .senhaAuth(new BCryptPasswordEncoder().encode(formUsuario.senha()))
                        .build();

        Usuario usuarioSalvo = usuarioRepository.save(usuarioBuilder);

        emailService.sendEmail(geraEmail(usuarioSalvo));

        return usuarioSalvo.getId();
    }

    @Override
    public List<Usuario> obterUsuarios()
    {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario obterUsuario(String id)
    {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("usuário", id)
        );
    }

    @Override
    public Usuario atualizarUsuario(UsuarioDto Usuario)
    {
        return null;
    }

    @Override
    public String excluirUsuario(String id)
    {
        usuarioRepository.deleteById(id);
        return "Usuario excluído com sucesso!";
    }

    private int retornaIdadeUsuario(String dataNascimento)
    {
        try
        {
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(dataNascimento, formatador);
            Period periodo = Period.between(date, LocalDate.now());
            return periodo.getYears();
        }
        catch (DateTimeParseException e)
        {
            throw new DateTimeParseException("Data de nascimento inválida", dataNascimento, 0, e);
        }
    }

    private EmailDto geraEmail(Usuario Usuario)
    {
        return
                EmailDto.builder()
                        .ownerRef("Bank")
                        .emailFrom("noreply-bank@gmail.com")
                        .emailTo(Usuario.getEmail())
                        .subject("Solicitação de conta")
                        .body(
                                "Olá, " + Usuario.getPrimeiroNome() + "."
                                + "\n\nSua solicitação de conta foi realizada com sucesso!"
                                + "\n\nIremos analisar o seu perfil o mais breve possível e entraremos em contato com você."
                                + "\n\nAtenciosamente, equipe Bank.")
                        .build();
    }
}
