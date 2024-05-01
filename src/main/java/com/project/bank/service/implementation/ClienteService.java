package com.project.bank.service.implementation;

import com.project.bank.entity.dto.ClienteDto;
import com.project.bank.entity.model.Cliente;
import com.project.bank.handler.BusinessException;
import com.project.bank.handler.RegistroNaoEncontradoException;
import com.project.bank.repository.ClienteRepository;
import com.project.bank.service.repository.ClienteServiceRep;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@Service
@RequiredArgsConstructor
public class ClienteService implements ClienteServiceRep
{
    private final ClienteRepository clienteRepository;
    @Override
    public Cliente cadastrarCliente(Cliente cliente)
    {
        if(cliente.getPrimeiroNome().length() <= 1 || cliente.getSobrenome().length() <= 1)
            throw new BusinessException("Nome e sobrenome devem ter mais de 1 caractere");
        if(cliente.getCpf().length() != 11)
            throw new BusinessException("CPF inválido");
        if(retornaIdadeCliente(cliente.getDataNascimento()) < 18)
            throw new BusinessException("Clientes devem ser maiores de 18 anos.");


        Cliente clienteConstruido =
                Cliente.builder()
                .primeiroNome(cliente.getPrimeiroNome())
                .sobrenome(cliente.getSobrenome())
                .cpf(cliente.getCpf())
                .dataNascimento(cliente.getDataNascimento())
                .email(cliente.getEmail())
                .numeroTelefone(cliente.getNumeroTelefone())
                .build();

        clienteRepository.save(clienteConstruido);

        return clienteConstruido;
    }

    @Override
    public Cliente obterCliente(long id)
    {
        return clienteRepository.findById(id).orElseThrow(
                () -> { throw new RegistroNaoEncontradoException("cliente", id); }
        );
    }

    @Override
    public Cliente atualizarCliente(ClienteDto cliente)
    {
        return null;
    }

    @Override
    public String excluirCliente(long id)
    {
        clienteRepository.deleteById(id);
        return "Cliente excluído com sucesso!";
    }

    private int retornaIdadeCliente(String dataNascimento) {
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
}
