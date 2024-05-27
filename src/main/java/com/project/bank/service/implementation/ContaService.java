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
import com.project.bank.repository.ContaRepository;
import com.project.bank.repository.SolicitacaoContaRepository;
import com.project.bank.service.repository.ContaServiceRep;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
@RequiredArgsConstructor
@Service
public class ContaService implements ContaServiceRep {
    private final ContaRepository contaRepository;
    private final SolicitacaoContaRepository solicitacaoContaRepository;
    private final EmailService emailService;
    @Override
    @Transactional
    public String aprovarConta(String solicitacaoId)
    {
        SolicitacaoConta solicitacaoConta = solicitacaoContaRepository.findById(solicitacaoId).orElseThrow(
                () -> new RegistroNaoEncontradoException("solicitação de conta", solicitacaoId)
        );
       solicitacaoConta.setSolicitacaoContaEnum(SolicitacaoContaEnum.APROVADA);
       Usuario usuario = Usuario.builder()
                    .primeiroNome(solicitacaoConta.getPrimeiroNome())
                    .sobrenome(solicitacaoConta.getSobrenome())
                    .cpf(solicitacaoConta.getCpf())
                    .dataNascimento(solicitacaoConta.getDataNascimento())
                    .email(solicitacaoConta.getEmail())
                    .numeroTelefone(solicitacaoConta.getNumeroTelefone())
                    .build();
        AcessoConta acessoConta = AcessoConta.builder()
                .login(solicitacaoConta.getCpf())
                .senhaAuth(solicitacaoConta.getSenhaAuth())
                .role(UserRole.USER)
                .build();
        Conta contaBuilder =
                Conta.builder()
                .agencia("0001")
                .numConta(this.geraNumeroConta())
                .tipoConta(TipoConta.CORRENTE)
                .statusConta(StatusConta.ATIVA)
                .saldo(0.0)
                .usuario(usuario)
                .acessoConta(acessoConta)
                .dataCriacao(LocalDateTime.now())
                .build();
        Conta contaCriada = contaRepository.save(contaBuilder);
        solicitacaoContaRepository.deleteById(solicitacaoConta.getId());
        emailService.sendEmail(geraEmail(contaCriada));

        return "Conta aprovada com sucesso!";
    }
    @Override
    public String reprovarConta(String solicitacaoId)
    {
        SolicitacaoConta solicitacaoConta = solicitacaoContaRepository.findById(solicitacaoId).orElseThrow(
                () -> new RegistroNaoEncontradoException("solicitação de conta", solicitacaoId)
        );
        solicitacaoContaRepository.deleteById(solicitacaoConta.getId());
        return "Conta reprovada com sucesso!";
    }

    private String geraNumeroConta()
    {
        Random rand = new Random();
        StringBuilder chaveBuilder = new StringBuilder();

        for (int i = 0; i < 7; i++)
        {
            if(i == 5)
                chaveBuilder.append("-");
            else
                chaveBuilder.append(rand.nextInt(10));
        }
        return chaveBuilder.toString();
    }
    private EmailDto geraEmail(Conta contaCriada)
    {
        return
                EmailDto.builder()
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
}
