package com.project.bank.service.repository;

import com.project.bank.entity.dto.EnderecoDto;
import com.project.bank.entity.form.EnderecoForm;
import com.project.bank.entity.model.Endereco;
import org.springframework.stereotype.Service;

@Service
public interface EnderecoServiceRep
{
    Endereco cadastrarEndereco(EnderecoForm endereco);
    Endereco obterEnderecoPeloUsuarioId(String clienteId);
    Endereco atualizarEndereco(EnderecoDto endereco);
    String excluirEndereco(long id);
}
