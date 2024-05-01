package com.project.bank.service.implementation;

import com.project.bank.entity.dto.ChavePixDto;
import com.project.bank.entity.model.ChavePix;
import com.project.bank.entity.model.Conta;
import com.project.bank.enumerator.TipoChave;
import com.project.bank.repository.ChavePixRepository;
import com.project.bank.repository.ContaRepository;
import com.project.bank.service.repository.ChavePixServiceRep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class ChavePixService implements ChavePixServiceRep
{
    private final ChavePixRepository chavePixRepository;
    private final ContaRepository contaRepository;

    @Override
    public ChavePix cadastrarChavePix(ChavePixDto chavePix)
    {
        Conta conta = contaRepository.findById(chavePix.getContaId()).orElseThrow( () -> {
                    throw new RuntimeException("Conta inexistente");
                }
        );
        List<ChavePix> chavesPix = listarChavesPixConta(chavePix.getContaId());

        for (ChavePix pix : chavesPix)
        {
            if (pix.getTipoChave().equals(chavePix.getTipoChave()))
                throw new RuntimeException("Você já cadastrou uma chave pix para este tipo");
        }

        ChavePix objConstruido =
                ChavePix.builder()
                        .tipoChave(chavePix.getTipoChave())
                        .conta(conta)
                        .chave(converteEnumEmString(conta, chavePix.getTipoChave()))
                        .build();
        chavePixRepository.save(objConstruido);
        return objConstruido;
    }

    @Override
    public List<ChavePix> listarChavesPixConta(long id) {
        return chavePixRepository.findAllByContaId(id);
    }

    @Override
    public void excluirChavePix(long id) {
        chavePixRepository.deleteById(id);
    }

    private String converteEnumEmString(Conta conta, TipoChave tipoChave)
    {
        String temp;
        temp = switch (tipoChave) {
            case CPF -> conta.getCliente().getCpf();
            case EMAIL -> conta.getCliente().getEmail();
            case NUMERO_TELEFONE -> conta.getCliente().getNumeroTelefone();
            case ALEATORIA -> geraChaveAleatoria();
            default -> throw new RuntimeException("Tipo de chave inválida");
        };
        return temp;
    }

    private String geraChaveAleatoria()
    {
        Random rand = new Random();
        StringBuilder chaveBuilder = new StringBuilder();
        for (int i = 0; i < 36; i++)
        {
            if(i == (10) || i == (15) || i == (20) || i == (25))
                chaveBuilder.append("-");
            else if(i % 3 == 0){
                char letraAleatoria = (char) (rand.nextInt(26) + 'a');
                chaveBuilder.append(letraAleatoria);
            }
            else
                chaveBuilder.append(rand.nextInt(10));
        }
        return chaveBuilder.toString();
    }


}
