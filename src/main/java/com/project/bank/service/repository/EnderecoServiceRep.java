package com.project.bank.service.repository;

import com.project.bank.entity.dto.EnderecoDto;
import com.project.bank.entity.model.Cliente;
import com.project.bank.entity.model.Endereco;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface EnderecoServiceRep
{
    Endereco cadastrarEndereco(Endereco endereco, Cliente cliente);
    Endereco obterEndereco(long id);
    Endereco atualizarEndereco(EnderecoDto endereco);
    String excluirEndereco(long id);
}
