package com.project.bank.service.implementation;

import com.project.bank.entity.form.ChavePixForm;
import com.project.bank.entity.model.ChavePix;
import com.project.bank.entity.model.Conta;
import com.project.bank.entity.model.Usuario;
import com.project.bank.enumeration.TipoChave;
import com.project.bank.handler.BusinessException;
import com.project.bank.handler.RegistroNaoEncontradoException;
import com.project.bank.repository.ChavePixRepository;
import com.project.bank.repository.ContaRepository;
import com.project.bank.repository.UsuarioRepository;
import com.project.bank.service.repository.ChavePixServiceRep;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class ChavePixService implements ChavePixServiceRep
{
    @Autowired
    private ChavePixRepository chavePixRepository;
    @Autowired
    private ContaRepository contaRepository;

    @Override
    public ChavePix cadastrarChavePix(ChavePixForm chavePixForm, String cpf)
    {
        Conta conta = contaRepository.findContaByUsuarioCpf(cpf);
        verificaExistenciaDaConta(conta, cpf);
        List<ChavePix> chavesPix = chavePixRepository.findAllByUsuarioCpf(cpf);
        verificaExistenciaDeChavePix(chavePixForm, chavesPix);
        ChavePix chavePixBuilder = ChavePix.builder()
                        .tipoChave(chavePixForm.tipoChave())
                        .conta(conta)
                        .chave(criaChavePix(conta, chavePixForm.tipoChave()))
                        .build();
        chavePixRepository.save(chavePixBuilder);
        return chavePixBuilder;
    }
    @Override
    public List<ChavePix> listarChavesPixConta(String cpf)
    {
        List<ChavePix> chavesPix = chavePixRepository.findAllByUsuarioCpf(cpf);
        verificaChavesPixConta(chavesPix);
        return chavesPix;
    }
    @Override
    public String excluirChavePix(ChavePixForm chavePixForm, String cpf)
    {
        List<ChavePix> chavesPix = chavePixRepository.findAllByUsuarioCpf(cpf);
        chavePixRepository.delete(encontraChavePix(chavesPix, chavePixForm));
        return "Chave PIX excluída com sucesso!";
    }
    private static String criaChavePix(Conta conta, TipoChave tipoChave)
    {
        return switch (tipoChave)
        {
            case CPF -> conta.getUsuario().getCpf();
            case EMAIL -> conta.getUsuario().getEmail();
            case NUMERO_TELEFONE -> conta.getUsuario().getNumeroTelefone();
            case ALEATORIA -> geraChaveAleatoria();
        };
    }
    private static String geraChaveAleatoria()
    {
        Random rand = new Random();
        StringBuilder chaveBuilder = new StringBuilder();
        List<Integer> posicoesSeparadoras = List.of(10,15,20,25);
        for (int i = 0; i < 36; i++)
        {
            if(posicoesSeparadoras.contains(i)) chaveBuilder.append("-");
            else if(i % 3 == 0) chaveBuilder.append(geraLetraAleatoria(rand));
            else chaveBuilder.append(geraNumeroAleatorio(rand));
        }
        return chaveBuilder.toString();
    }
    private static char geraLetraAleatoria(Random rand)
    {
        return (char) (rand.nextInt(26) + 'a');
    }
    private static int geraNumeroAleatorio(Random rand)
    {
        return rand.nextInt(0, 9);
    }
    private static void verificaExistenciaDeChavePix(ChavePixForm chavePixForm, List<ChavePix> chavesPix)
    {
        for (ChavePix pix : chavesPix)
            if (pix.getTipoChave().equals(chavePixForm.tipoChave()))
                throw new BusinessException("Você já cadastrou uma chave pix para este tipo");
    }
    private static void verificaExistenciaDaConta(Conta conta, String cpf)
    {
        if (conta == null) throw new RegistroNaoEncontradoException("conta", cpf);
    }
    private static void verificaChavesPixConta(List<ChavePix> chavesPix)
    {
        if (chavesPix.isEmpty())
            throw new BusinessException("Nenhuma chave pix encontrada");
    }
    private static ChavePix encontraChavePix(List<ChavePix> chavesPix, ChavePixForm chavePixForm)
    {
        for(ChavePix chavePix : chavesPix) if (chavePix.getTipoChave().equals(chavePixForm.tipoChave())) return chavePix;
        throw new BusinessException("Chave pix não encontrada");
    }

}
