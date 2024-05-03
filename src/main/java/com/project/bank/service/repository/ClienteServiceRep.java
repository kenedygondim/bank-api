package com.project.bank.service.repository;

import com.project.bank.entity.dto.ClienteDto;
import com.project.bank.entity.model.Cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClienteServiceRep
{
    Cliente cadastrarCliente(Cliente cliente);
    List<Cliente> obterClientes();
    Cliente obterCliente(long id);
    Cliente atualizarCliente(ClienteDto cliente);
    String excluirCliente(long id);
}
