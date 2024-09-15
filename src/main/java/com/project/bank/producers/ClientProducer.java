package com.project.bank.producers;

import com.project.bank.email.EmailDto;
import com.project.bank.entity.model.AccountRequest;
import com.project.bank.enumeration.TypeMessageEmailEnum;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClientProducer {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ClientProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(AccountRequest accountRequest, TypeMessageEmailEnum typeMessageEmailEnum) {
        EmailDto emailDto = new EmailDto();
        emailDto.setUserId(accountRequest.getId());
        emailDto.setEmailTo(accountRequest.getEmail());
        rabbitTemplate.convertAndSend("", routingKey, generateSubjectAndBody(accountRequest, emailDto, typeMessageEmailEnum));
    }

    private EmailDto generateSubjectAndBody(AccountRequest accountRequest, EmailDto emailDto, TypeMessageEmailEnum typeMessageEmailEnum) {
        if (typeMessageEmailEnum.equals(TypeMessageEmailEnum.REQUEST)) {
            emailDto.setEmailSubject("Solicitação de conta");
            emailDto.setEmailBody("Olá, " + accountRequest.getFirstName() + "."
                    + "\n\nSua solicitação de conta foi realizada com sucesso!"
                    + "\n\nIremos analisar o seu perfil o mais breve possível e entraremos em contato com você."
                    + "\n\nAtenciosamente, equipe Bank.");
        } else if (typeMessageEmailEnum.equals(TypeMessageEmailEnum.WELCOME)) {
            emailDto.setEmailSubject("Solicitação de conta");
            emailDto.setEmailBody(
                    "Olá, " + accountRequest.getFirstName() + "."
                            + "\n\nSua solicitação de conta foi aprovada!"
                            + "\n\nAcesse o aplicativo com seu CPF e password cadastrados na solicitação."
                            + "\n\nAtenciosamente, equipe Bank.");
        } else if (typeMessageEmailEnum.equals(TypeMessageEmailEnum.ACCOUNTDISAPPROVED)) {
            emailDto.setEmailSubject("Solicitação de conta");
            emailDto.setEmailBody(
                    "Olá, " + accountRequest.getFirstName() + "."
                            + "\n\nLamentamos, sua solicitação de conta foi reprovada!"
                            + "\n\nSeu perfil, no momento, não se enquadra nos pré-requisitos."
                            + "\n\nEsperamos encontrá-lo em uma oportunidade futura."
                            + "\n\nAtenciosamente, equipe Bank.");
        }

        return emailDto;
    }
}

