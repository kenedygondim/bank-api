package com.project.bank.service.implementation;
import com.project.bank.email.EmailDto;
import com.project.bank.email.EmailService;
import com.project.bank.entity.dto.ContaDto;
import com.project.bank.entity.model.AcessoConta;
import com.project.bank.entity.model.Conta;
import com.project.bank.entity.model.SolicitacaoConta;
import com.project.bank.entity.model.Usuario;
import com.project.bank.enumeration.SolicitacaoContaEnum;
import com.project.bank.enumeration.StatusConta;
import com.project.bank.enumeration.TipoConta;
import com.project.bank.enumeration.UserRole;
import com.project.bank.handler.RegistroNaoEncontradoException;
import com.project.bank.repository.AcessoContaRepository;
import com.project.bank.repository.ContaRepository;
import com.project.bank.repository.SolicitacaoContaRepository;
import com.project.bank.service.repository.ContaServiceRep;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
@RequiredArgsConstructor
@Service
public class ContaService implements ContaServiceRep {
    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private SolicitacaoContaService solicitacaoContaService;
    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public String aprovarConta(String solicitacaoId)
    {
        SolicitacaoConta solicitacaoConta = solicitacaoContaService.retornarSolicitacaoConta(solicitacaoId);
        Conta conta = criarObjetoConta(solicitacaoConta);
        emailService.sendEmail(gerarEmailContaAprovada(conta));
        contaRepository.save(conta);
        solicitacaoContaService.excluirSolicitacaoConta(solicitacaoId);
        return "Conta aprovada com sucesso!";
    }
    @Override
    public String reprovarConta(String solicitacaoId)
    {
        SolicitacaoConta solicitacaoConta = solicitacaoContaService.retornarSolicitacaoConta(solicitacaoId);
        solicitacaoContaService.excluirSolicitacaoConta(solicitacaoId);
        emailService.sendEmail(gerarEmailContaReprovada(solicitacaoConta));
        return "Conta reprovada com sucesso!";
    }

    public Conta retornarConta(String cpf)
    {
        return contaRepository.findContaByUsuarioCpf(cpf).orElseThrow(
                () -> new RegistroNaoEncontradoException("usuário", cpf)
        );
    }

    private static Usuario criarObjetoUsuario(SolicitacaoConta solicitacaoConta)
    {
        return Usuario.builder()
                .primeiroNome(solicitacaoConta.getPrimeiroNome())
                .sobrenome(solicitacaoConta.getSobrenome())
                .cpf(solicitacaoConta.getCpf())
                .dataNascimento(solicitacaoConta.getDataNascimento())
                .email(solicitacaoConta.getEmail())
                .numeroTelefone(solicitacaoConta.getNumeroTelefone())
                .build();
    }

    private static AcessoConta criarAcessoConta(SolicitacaoConta solicitacaoConta)
    {
        return AcessoConta.builder()
                .login(solicitacaoConta.getCpf())
                .senhaAuth(solicitacaoConta.getSenhaAuth())
                .role(UserRole.USER)
                .build();
    }

    private static Conta criarObjetoConta(SolicitacaoConta solicitacaoConta)
    {
       return Conta.builder()
                        .agencia("0001")
                        .numConta(gerarNumeroConta())
                        .tipoConta(TipoConta.CORRENTE)
                        .statusConta(StatusConta.ATIVA)
                        .saldo(0.0)
                        .usuario(criarObjetoUsuario(solicitacaoConta))
                        .acessoConta(criarAcessoConta(solicitacaoConta))
                        .dataCriacao(LocalDateTime.now())
                        .build();
    }

    private static String gerarNumeroConta()
    {
        Random rand = new Random();
        StringBuilder numeroContaBuilder = new StringBuilder();
        for (int i = 0; i < 7; i++)
            if (i == 5) numeroContaBuilder.append("-"); else numeroContaBuilder.append(gerarNumeroAleatorio(rand));
        return numeroContaBuilder.toString();
    }
    private static EmailDto gerarEmailContaAprovada(Conta contaCriada)
    {
        return EmailDto.builder()
                        .ownerRef("Bank")
                        .emailFrom("noreply-bank@gmail.com")
                        .emailTo(contaCriada.getUsuario().getEmail())
                        .subject("Solicitação de conta")
                        .body(
                                "Olá, " + contaCriada.getUsuario().getPrimeiroNome() + "."
                                        + "\n\nSua solicitação de conta foi aprovada!"
                                        + "\n\nAcesse o aplicativo com seu CPF e senha cadastrados na solicitação."
                                        + "\nConta: " + contaCriada.getNumConta()
                                        + "\nAgência: " + contaCriada.getAgencia()
                                        + "\n\nAtenciosamente, equipe Bank.")
                        .build();
    }

    private static EmailDto gerarEmailContaReprovada(SolicitacaoConta solicitacaoConta)
    {
        return EmailDto.builder()
                .ownerRef("Bank")
                .emailFrom("noreply-bank@gmail.com")
                .emailTo(solicitacaoConta.getEmail())
                .subject("Solicitação de conta")
                .body(
                        "Olá, " + solicitacaoConta.getPrimeiroNome() + "."
                                + "\n\nLamentamos, sua solicitação de conta foi reprovada!"
                                + "\n\nSeu perfil, no momento, não se enquadra nos pré-requisitos."
                                + "\n\nEsperamos encontrá-lo em uma oportunidade futura."
                                + "\n\nAtenciosamente, equipe Bank.")
                .build();
    }

    private static int gerarNumeroAleatorio(Random rand)
    {
        return rand.nextInt(10);
    }
}
