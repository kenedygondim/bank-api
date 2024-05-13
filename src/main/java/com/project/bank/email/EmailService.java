package com.project.bank.email;

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
}
