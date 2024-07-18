package com.project.bank.service.implementation;

import com.project.bank.entity.dto.EnderecoDto;
import com.project.bank.entity.form.EnderecoForm;
import com.project.bank.entity.model.Endereco;
import com.project.bank.entity.model.Usuario;
import com.project.bank.feign.EnderecoFeign;
import com.project.bank.feign.response.EnderecoResponse;
import com.project.bank.handler.BusinessException;
import com.project.bank.handler.RegistroNaoEncontradoException;
import com.project.bank.repository.EnderecoRepository;
import com.project.bank.repository.UsuarioRepository;
import com.project.bank.service.repository.EnderecoServiceRep;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EnderecoService implements EnderecoServiceRep {
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EnderecoFeign enderecoFeign;

    @Override
    public Endereco cadastrarEndereco(EnderecoForm enderecoForm, String cpf)
    {
        Usuario usuario = usuarioService.obterUsuario(cpf);
        verificarExistenciaEndereco(usuario.getEndereco());
        Optional<EnderecoResponse> enderecoResponse = enderecoFeign.buscaEnderecoCep(enderecoForm.cep());
        Endereco endereco = criarObjetoEndereco(verificarExistenciaCep(enderecoResponse), enderecoForm, usuario);
        return enderecoRepository.save(endereco);
    }
    @Override
    public Endereco obterEnderecoPeloCpf(String cpf)
    {
        return usuarioService.obterUsuario(cpf).getEndereco();
    }
    @Override
    public Endereco atualizarEndereco(EnderecoDto endereco) {
        return null;
    }
    @Override
    public String excluirEndereco(long id) {
        return null;
    }

    private static void verificarExistenciaEndereco(Endereco endereco)
    {
        if (endereco != null) throw new BusinessException("O usuario já possui um endereço cadastrado.");
    }
    private static EnderecoResponse verificarExistenciaCep(Optional<EnderecoResponse> enderecoResponse)
    {
        if(enderecoResponse.isPresent()) return new ModelMapper().map(enderecoResponse, EnderecoResponse.class);
        throw new BusinessException("CEP não encontrado");
    }
    private static Endereco criarObjetoEndereco(EnderecoResponse enderecoResponse, EnderecoForm enderecoForm, Usuario usuario)
    {
        return Endereco.builder()
                .cep(enderecoResponse.getCep())
                .uf(enderecoResponse.getUf())
                .cidade(enderecoResponse.getLocalidade())
                .bairro(enderecoResponse.getBairro())
                .logradouro(enderecoResponse.getLogradouro())
                .numero(enderecoForm.numero())
                .complemento(enderecoForm.complemento())
                .usuario(usuario)
                .build();
    }
}
