package com.project.bank.email;

import com.project.bank.entity.model.AccountRequest;
import com.project.bank.entity.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService
{
    private final JavaMailSender javaMailSender;

    public void sendEmail(EmailDto emailDto)
    {
        Email email = new Email();
        BeanUtils.copyProperties(emailDto, email); //'converte' dto para model e vice-versa

        email.setSendDateEmail(LocalDateTime.now());
        try
        {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getSubject());
            message.setText(email.getBody());

            javaMailSender.send(message);
        }
        catch (MailException e)
        {
            throw new RuntimeException("Erro ao enviar email");
        }
    }

    public EmailDto generateRequestEmail(AccountRequest accountRequest) {
        return EmailDto.builder()
                        .ownerRef("Bank")
                        .emailFrom("noreply-bank@gmail.com")
                        .emailTo(accountRequest.getEmail())
                        .subject("Solicitação de conta")
                        .body(
                                "Olá, " + accountRequest.getFirstName() + "."
                                        + "\n\nSua solicitação de conta foi realizada com sucesso!"
                                        + "\n\nIremos analisar o seu perfil o mais breve possível e entraremos em contato com você."
                                        + "\n\nAtenciosamente, equipe Bank.")
                        .build();
    }
    public EmailDto generateApprovedAccountEmail(Account accountCriada)
    {
        return EmailDto.builder()
                .ownerRef("Bank")
                .emailFrom("noreply-bank@gmail.com")
                .emailTo(accountCriada.getClient().getEmail())
                .subject("Solicitação de conta")
                .body(
                        "Olá, " + accountCriada.getClient().getFirstName() + "."
                                + "\n\nSua solicitação de conta foi aprovada!"
                                + "\n\nAcesse o aplicativo com seu CPF e password cadastrados na solicitação."
                                + "\nConta: " + accountCriada.getAccountNumber()
                                + "\nAgência: " + accountCriada.getBranchNumber()
                                + "\n\nAtenciosamente, equipe Bank.")
                .build();
    }

    public EmailDto generateDisapprovedAccountEmail(AccountRequest accountRequest)
    {
        return EmailDto.builder()
                .ownerRef("Bank")
                .emailFrom("noreply-bank@gmail.com")
                .emailTo(accountRequest.getEmail())
                .subject("Solicitação de conta")
                .body(
                        "Olá, " + accountRequest.getFirstName() + "."
                                + "\n\nLamentamos, sua solicitação de conta foi reprovada!"
                                + "\n\nSeu perfil, no momento, não se enquadra nos pré-requisitos."
                                + "\n\nEsperamos encontrá-lo em uma oportunidade futura."
                                + "\n\nAtenciosamente, equipe Bank.")
                .build();
    }

}
