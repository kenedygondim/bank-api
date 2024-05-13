package com.project.bank.service.implementation;

import com.project.bank.entity.dto.EnderecoDto;
import com.project.bank.entity.form.EnderecoForm;
import com.project.bank.entity.model.Cliente;
import com.project.bank.entity.model.Endereco;
import com.project.bank.feign.EnderecoFeign;
import com.project.bank.feign.response.EnderecoResponse;
import com.project.bank.handler.BusinessException;
import com.project.bank.handler.RegistroNaoEncontradoException;
import com.project.bank.repository.ClienteRepository;
import com.project.bank.repository.EnderecoRepository;
import com.project.bank.service.repository.EnderecoServiceRep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EnderecoService implements EnderecoServiceRep {

    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;
    private final EnderecoFeign enderecoFeign;

    @Override
    public Endereco cadastrarEndereco(EnderecoForm endereco)
    {
        Cliente cliente = clienteRepository.findById(endereco.clienteId()).orElseThrow(
                () -> new RegistroNaoEncontradoException("cliente", endereco.clienteId())
        );

        if(cliente.getEndereco() != null)
            throw new BusinessException("O cliente já possui um endereço cadastrado.");

        EnderecoResponse enderecoResponse = enderecoFeign.buscaEnderecoCep(endereco.cep()).orElseThrow(
                () -> new BusinessException("CEP não encontrado.")
        );

        Endereco objConstruido = Endereco.builder()
                .cep(enderecoResponse.getCep())
                .uf(enderecoResponse.getUf())
                .cidade(enderecoResponse.getLocalidade())
                .bairro(enderecoResponse.getBairro())
                .logradouro(enderecoResponse.getLogradouro())
                .numero(endereco.numero())
                .complemento(endereco.complemento())
                .cliente(cliente)
                .build();

        return enderecoRepository.save(objConstruido);
    }

    @Override
    public Endereco obterEnderecoPeloClienteId(String clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(
                () -> new RegistroNaoEncontradoException("cliente", clienteId)
        );

        if(cliente.getEndereco() == null)
            throw new BusinessException("O cliente não possui endereço cadastrado.");

        return cliente.getEndereco();
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
