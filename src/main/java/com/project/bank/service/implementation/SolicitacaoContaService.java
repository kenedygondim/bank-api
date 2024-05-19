package com.project.bank.service.implementation;
import com.project.bank.email.EmailDto;
import com.project.bank.email.EmailService;
import com.project.bank.entity.form.SolicitacaoContaForm;
import com.project.bank.entity.model.AcessoConta;
import com.project.bank.entity.model.SolicitacaoConta;
import com.project.bank.enumeration.UserRole;
import com.project.bank.handler.BusinessException;
import com.project.bank.repository.AcessoContaRepository;
import com.project.bank.repository.SolicitacaoContaRepository;
import com.project.bank.repository.UsuarioRepository;
import com.project.bank.service.repository.SolicitacaoContaRep;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
@Service
@RequiredArgsConstructor
public class SolicitacaoContaService implements SolicitacaoContaRep
{
    private final EmailService emailService;
    private final SolicitacaoContaRepository solicitacaoContaRepository;
    private final UsuarioRepository usuarioRepository;
    @Override
    @Transactional
    public String solicitarConta(SolicitacaoContaForm formSolicitacaoConta)
    {
        if (retornaIdadeUsuario(formSolicitacaoConta.dataNascimento()) < 18)
            throw new BusinessException("Usuários devem ser maiores de 18 anos.");

        verificaCamposExistentes(formSolicitacaoConta);
        verificaSolicitacaoExistente(formSolicitacaoConta.cpf());

        SolicitacaoConta solicitacaoContaBuilder =
                SolicitacaoConta.builder()
                        .primeiroNome(formSolicitacaoConta.primeiroNome())
                        .sobrenome(formSolicitacaoConta.sobrenome())
                        .cpf(formSolicitacaoConta.cpf())
                        .dataNascimento(formSolicitacaoConta.dataNascimento())
                        .email(formSolicitacaoConta.email())
                        .numeroTelefone(formataNumeroTelefone(formSolicitacaoConta.numeroTelefone()))
                        .senhaAuth(new BCryptPasswordEncoder().encode(formSolicitacaoConta.senha()))
                        .build();

        try
        {
            SolicitacaoConta solicitacaoConta = solicitacaoContaRepository.save(solicitacaoContaBuilder);
            emailService.sendEmail(geraEmail(solicitacaoConta));
            return solicitacaoConta.getId();
        } catch (Exception e)
        {
            throw new BusinessException("Erro ao solicitar conta.");
        }
    }
    private String formataNumeroTelefone(String numeroTelefone)
    {
        return numeroTelefone.replaceAll("[^0-9]", "");
    }
    private void verificaCamposExistentes(SolicitacaoContaForm formSolicitacaoConta)
    {
        if (usuarioRepository.existsByCpf(formSolicitacaoConta.cpf()))
            throw new BusinessException("CPF já cadastrado.");
        if (usuarioRepository.existsByEmail(formSolicitacaoConta.email()))
            throw new BusinessException("Email já cadastrado.");
        if (usuarioRepository.existsByNumeroTelefone(formSolicitacaoConta.numeroTelefone()))
            throw new BusinessException("Número de telefone já cadastrado.");
    }
    private void verificaSolicitacaoExistente(String cpf)
    {
        if (solicitacaoContaRepository.existsByCpf(cpf))
            throw new BusinessException("Solicitação de conta já realizada.");
    }
    private int retornaIdadeUsuario(String dataNascimento)
    {
        try
        {
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(dataNascimento, formatador);
            Period periodo = Period.between(date, LocalDate.now());
            return periodo.getYears();
        } catch (DateTimeParseException e)
        {
            throw new DateTimeParseException("Data de nascimento inválida", dataNascimento, 0, e);
        }
    }
    private EmailDto geraEmail(SolicitacaoConta solicitacaoConta)
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
