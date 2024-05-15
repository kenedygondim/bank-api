package com.project.bank.service.implementation;

import com.project.bank.email.EmailDto;
import com.project.bank.email.EmailService;
import com.project.bank.entity.dto.ContaDto;
import com.project.bank.entity.model.Usuario;
import com.project.bank.entity.model.Conta;
import com.project.bank.enumeration.SolicitacaoConta;
import com.project.bank.enumeration.StatusConta;
import com.project.bank.enumeration.TipoConta;
import com.project.bank.handler.RegistroNaoEncontradoException;
import com.project.bank.repository.UsuarioRepository;
import com.project.bank.repository.ContaRepository;
import com.project.bank.service.repository.ContaServiceRep;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@AllArgsConstructor
@Service
public class ContaService implements ContaServiceRep {

    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;


    @Override
    public void aprovarConta(String id)
    {
        Usuario cliente = usuarioRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("cliente", id)
        );

        cliente.setSolicitacaoConta(SolicitacaoConta.APROVADA);

        Conta contaBuilder =
                Conta.builder()
                .agencia("0001")
                .numConta(this.geraNumeroConta())
                .tipoConta(TipoConta.CORRENTE)
                .statusConta(StatusConta.ATIVA)
                .saldo(0.0)
                .usuario(cliente)
                .dataCriacao(LocalDateTime.now())
                .build();

        Conta contaCriada = contaRepository.save(contaBuilder);

        emailService.sendEmail(geraEmail(contaCriada));
    }

    @Override
    public void reprovarConta(String id)
    {
        Usuario cliente = usuarioRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("cliente", id)
        );

        usuarioRepository.deleteById(cliente.getId());
    }

    @Override
    public Conta atualizarConta(ContaDto conta) {
        return null;
    }

    @Override
    public String excluirConta(long id) {
        return null;
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
                                        + "\n\nAcesse o aplicativo com seu CPF e senha cadastrados."
                                        + "\nConta: " + contaCriada.getNumConta()
                                        + "\nAgência: " + contaCriada.getAgencia()
                                        + "\n\nAtenciosamente, equipe Bank.")
                        .build();
    }
}
