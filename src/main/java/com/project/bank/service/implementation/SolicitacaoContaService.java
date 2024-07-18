package com.project.bank.service.implementation;

import com.project.bank.email.EmailDto;
import com.project.bank.email.EmailService;
import com.project.bank.entity.dto.SolicitacaoContaDto;
import com.project.bank.entity.form.SolicitacaoContaForm;
import com.project.bank.entity.model.AcessoConta;
import com.project.bank.entity.model.SolicitacaoConta;
import com.project.bank.enumeration.UserRole;
import com.project.bank.handler.BusinessException;
import com.project.bank.handler.RegistroDuplicadoException;
import com.project.bank.handler.RegistroNaoEncontradoException;
import com.project.bank.repository.AcessoContaRepository;
import com.project.bank.repository.SolicitacaoContaRepository;
import com.project.bank.repository.UsuarioRepository;
import com.project.bank.service.repository.SolicitacaoContaRep;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitacaoContaService implements SolicitacaoContaRep
{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private final SolicitacaoContaRepository solicitacaoContaRepository;
    @Autowired
    private final UsuarioRepository usuarioRepository;
    @Autowired
    private AcessoContaRepository acessoContaRepository;

    @Override
    @Transactional
    public String solicitarConta(SolicitacaoContaForm formSolicitacaoConta)
    {
        verificarIdadeUsuario(formSolicitacaoConta.dataNascimento());
        verificarCamposExistentes(formSolicitacaoConta);
        verificarSolicitacaoExistente(formSolicitacaoConta.cpf());
        criarObjetoSolicitacaoConta(formSolicitacaoConta);
        SolicitacaoConta solicitacaoConta = solicitacaoContaRepository.save(criarObjetoSolicitacaoConta(formSolicitacaoConta));
        emailService.sendEmail(gerarEmail(solicitacaoConta));
        return "Conta solicitada com sucesso. Verifique seu email para mais informações.";
    }

    @Override
    public List<SolicitacaoConta> obterSolicitacoes() {
        return solicitacaoContaRepository.findAll();
    }
    @Override
    public  void contaAdmin() {
        String BCryptPasswordEncoder;
        AcessoConta acessoConta = AcessoConta.builder()
                .login("admin")
                .role(UserRole.ADMIN)
                .senhaAuth(new BCryptPasswordEncoder().encode("admin"))
                .build();
        acessoContaRepository.save(acessoConta);
    }
    /*@Override
    public void contaAdmin() {
        AcessoConta acessoConta = AcessoConta.builder()
                .login("admin")
                .senhaAuth(new BCryptPasswordEncoder().encode("admin"))
                .role(UserRole.ADMIN)
                .build();
        acessoContaRepository.save(acessoConta);
    }*/

    public SolicitacaoConta retornarSolicitacaoConta(String solicitacaoId)
    {
        return solicitacaoContaRepository.findById(solicitacaoId).orElseThrow(
                () -> new RegistroNaoEncontradoException("solicitação de conta", solicitacaoId)
        );
    }
    public void excluirSolicitacaoConta(String solicitacaoId)
    {
         solicitacaoContaRepository.deleteById(solicitacaoId);
    }

    private static String formatarNumeroTelefone(String numeroTelefone)
    {
        return numeroTelefone.replaceAll("[^0-9]", "");
    }
    private static SolicitacaoConta criarObjetoSolicitacaoConta(SolicitacaoContaForm formSolicitacaoConta)
    {
        return SolicitacaoConta.builder()
                .primeiroNome(formSolicitacaoConta.primeiroNome())
                .sobrenome(formSolicitacaoConta.sobrenome())
                .cpf(formSolicitacaoConta.cpf())
                .dataNascimento(formSolicitacaoConta.dataNascimento())
                .email(formSolicitacaoConta.email())
                .numeroTelefone(formatarNumeroTelefone(formSolicitacaoConta.numeroTelefone()))
                .senhaAuth(new BCryptPasswordEncoder().encode(formSolicitacaoConta.senha()))
                .build();
    }
    private void verificarCamposExistentes(SolicitacaoContaForm formSolicitacaoConta)
    {
        if (usuarioRepository.existsByCpf(formSolicitacaoConta.cpf()))
            throw new RegistroDuplicadoException("CPF");
        if (usuarioRepository.existsByEmail(formSolicitacaoConta.email()))
            throw new RegistroDuplicadoException("email");
        if (usuarioRepository.existsByNumeroTelefone(formSolicitacaoConta.numeroTelefone()))
            throw new RegistroDuplicadoException("número de telefone");
    }
    private void verificarSolicitacaoExistente(String cpf)
    {
        if (solicitacaoContaRepository.existsByCpf(cpf))
            throw new BusinessException("Solicitação de conta já realizada.");
    }
    private void verificarIdadeUsuario(String dataNascimento)
    {
        try
        {
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(dataNascimento, formatador);
            Period periodo = Period.between(date, LocalDate.now());
            if(periodo.getYears() < 18)
                throw new BusinessException("Usuários devem ser maiores de 18 anos.");
        } catch (DateTimeParseException e)
        {
            throw new DateTimeParseException("Data de nascimento inválida", dataNascimento, 0, e);
        }
    }
    private EmailDto gerarEmail(SolicitacaoConta solicitacaoConta)
    {
        return
                EmailDto.builder()
                        .ownerRef("Bank")
                        .emailFrom("noreply-bank@gmail.com")
                        .emailTo(solicitacaoConta.getEmail())
                        .subject("Solicitação de conta")
                        .body(
                                "Olá, " + solicitacaoConta.getPrimeiroNome() + "."
                                        + "\n\nSua solicitação de conta foi realizada com sucesso!"
                                        + "\n\nIremos analisar o seu perfil o mais breve possível e entraremos em contato com você."
                                        + "\n\nAtenciosamente, equipe Bank.")
                        .build();
    }
}
