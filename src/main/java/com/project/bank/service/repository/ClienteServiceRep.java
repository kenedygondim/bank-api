package com.project.bank.service.repository;

import com.project.bank.entity.dto.ClienteDto;
import com.project.bank.entity.form.RegisterForm;
import com.project.bank.entity.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClienteServiceRep
{
    String solicitarConta(RegisterForm cliente);
    List<Cliente> obterClientes();
    Cliente obterCliente(String id);
    Cliente atualizarCliente(ClienteDto cliente);
    String excluirCliente(String id);
}
