package com.project.bank.service.repository;

import com.project.bank.entity.dto.EnderecoDto;
import com.project.bank.entity.form.EnderecoForm;
import com.project.bank.entity.model.Cliente;
import com.project.bank.entity.model.Endereco;
import com.project.bank.feign.response.EnderecoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface EnderecoServiceRep
{
    Endereco cadastrarEndereco(EnderecoForm endereco);
    Endereco obterEnderecoPeloClienteId(String clienteId);
    Endereco atualizarEndereco(EnderecoDto endereco);
    String excluirEndereco(long id);
}
