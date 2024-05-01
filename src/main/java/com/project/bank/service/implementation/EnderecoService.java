package com.project.bank.service.implementation;

import com.project.bank.entity.dto.EnderecoDto;
import com.project.bank.entity.model.Cliente;
import com.project.bank.entity.model.Endereco;
import com.project.bank.service.repository.EnderecoServiceRep;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService implements EnderecoServiceRep {
    @Override
    public Endereco cadastrarEndereco(Endereco endereco, Cliente cliente) {
        return null;
    }

    @Override
    public Endereco obterEndereco(long id) {
        return null;
    }

    @Override
    public Endereco atualizarEndereco(EnderecoDto endereco) {
        return null;
    }

    @Override
    public String excluirEndereco(long id) {
        return null;
    }
}
