package com.project.bank.service.implementation;

import com.project.bank.email.EmailDto;
import com.project.bank.email.EmailService;
import com.project.bank.entity.dto.ClienteDto;
import com.project.bank.entity.form.RegisterForm;
import com.project.bank.entity.model.Cliente;
import com.project.bank.entity.model.Conta;
import com.project.bank.handler.BusinessException;
import com.project.bank.handler.RegistroNaoEncontradoException;
import com.project.bank.repository.ClienteRepository;
import com.project.bank.service.repository.ClienteServiceRep;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ClienteService implements ClienteServiceRep
{
    private final ClienteRepository clienteRepository;
    private final ContaService contaService;
    private final EmailService emailService;

    @Override
    @Transactional
    public String solicitarConta(RegisterForm formCliente)
    {
        if(formCliente.primeiroNome().length() <= 1 || formCliente.sobrenome().length() <= 1)
            throw new BusinessException("Nome e sobrenome devem ter mais de 1 caractere");
        if(formCliente.cpf().length() != 11)
            throw new BusinessException("CPF inválido");
        if(retornaIdadeCliente(formCliente.dataNascimento()) < 18)
            throw new BusinessException("Clientes devem ser maiores de 18 anos.");
        if(formCliente.numeroTelefone().length() != 11)
            throw new BusinessException("Insira o número de telefone no formato: 11999999999");

        Cliente clienteBuilder =
                Cliente.builder()
                        .primeiroNome(formCliente.primeiroNome())
                        .sobrenome(formCliente.sobrenome())
                        .cpf(formCliente.cpf())
                        .dataNascimento(formCliente.dataNascimento())
                        .email(formCliente.email())
                        .numeroTelefone(formCliente.numeroTelefone())
                        .senhaAuth(new BCryptPasswordEncoder().encode(formCliente.senha()))
                        .build();

        Cliente clienteSalvo = clienteRepository.save(clienteBuilder);

        emailService.sendEmail(geraEmail(clienteSalvo));

        return clienteSalvo.getId();
    }

    @Override
    public List<Cliente> obterClientes()
    {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente obterCliente(String id)
    {
        return clienteRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("cliente", id)
        );
    }

    @Override
    public Cliente atualizarCliente(ClienteDto cliente)
    {
        return null;
    }

    @Override
    public String excluirCliente(String id)
    {
        clienteRepository.deleteById(id);
        return "Cliente excluído com sucesso!";
    }

    private int retornaIdadeCliente(String dataNascimento)
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

    private EmailDto geraEmail(Cliente cliente)
    {
        return
                EmailDto.builder()
                        .ownerRef("Bank")
                        .emailFrom("noreply-bank@gmail.com")
                        .emailTo(cliente.getEmail())
                        .subject("Solicitação de conta")
                        .body(
                                "Olá, " + cliente.getPrimeiroNome() + "."
                                + "\n\nSua solicitação de conta foi realizada com sucesso!"
                                + "\n\nIremos analisar o seu perfil o mais breve possível e entraremos em contato com você."
                                + "\n\nAtenciosamente, equipe Bank.")
                        .build();
    }
}
