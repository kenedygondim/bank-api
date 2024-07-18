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
    private ContaService contaService;

    @Override
    public ChavePix cadastrarChavePix(ChavePixForm chavePixForm, String cpf)
    {
        Conta conta = contaService.retornarConta(cpf);
        List<ChavePix> chavesPix = chavePixRepository.findAllByUsuarioCpf(cpf);
        verificarExistenciaDeChavePix(chavePixForm, chavesPix);
        return chavePixRepository.save(criarObjetoChavePix(conta, chavePixForm.tipoChave()));
    }
    @Override
    public List<ChavePix> listarChavesPixConta(String cpf)
    {
        List<ChavePix> chavesPix = chavePixRepository.findAllByUsuarioCpf(cpf);
        verificarChavesPixConta(chavesPix);
        return chavesPix;
    }
    @Override
    public String excluirChavePix(ChavePixForm chavePixForm, String cpf)
    {
        List<ChavePix> chavesPix = chavePixRepository.findAllByUsuarioCpf(cpf);
        chavePixRepository.delete(encontrarChavePix(chavesPix, chavePixForm));
        return "Chave PIX excluída com sucesso!";
    }
    public ChavePix retornarChavePix (String chave)
    {
        return chavePixRepository.findByChave(chave).orElseThrow(
                () -> new RegistroNaoEncontradoException("chave pix", chave)
        );
    }

    private static ChavePix criarObjetoChavePix (Conta conta, TipoChave tipoChave)
    {
        return ChavePix.builder()
                .tipoChave(tipoChave)
                .conta(conta)
                .chave(criarChavePix(conta, tipoChave))
                .build();
    }
    private static String criarChavePix(Conta conta, TipoChave tipoChave)
    {
        return switch (tipoChave)
        {
            case CPF -> conta.getUsuario().getCpf();
            case EMAIL -> conta.getUsuario().getEmail();
            case NUMERO_TELEFONE -> conta.getUsuario().getNumeroTelefone();
            case ALEATORIA -> gerarChaveAleatoria();
        };
    }
    private static String gerarChaveAleatoria()
    {
        Random rand = new Random();
        StringBuilder chaveBuilder = new StringBuilder();
        List<Integer> posicoesSeparadoras = List.of(10,15,20,25);
        for (int i = 0; i < 36; i++)
        {
            if(posicoesSeparadoras.contains(i)) chaveBuilder.append("-");
            else if(i % 3 == 0) chaveBuilder.append(gerarLetraAleatoria(rand));
            else chaveBuilder.append(gerarNumeroAleatorio(rand));
        }
        return chaveBuilder.toString();
    }
    private static char gerarLetraAleatoria(Random rand)
    {
        return (char) (rand.nextInt(26) + 'a');
    }
    private static int gerarNumeroAleatorio(Random rand)
    {
        return rand.nextInt(0, 9);
    }
    private static void verificarExistenciaDeChavePix(ChavePixForm chavePixForm, List<ChavePix> chavesPix)
    {
        for (ChavePix pix : chavesPix)
            if (pix.getTipoChave().equals(chavePixForm.tipoChave()))
                throw new BusinessException("Você já cadastrou uma chave pix para este tipo");
    }
    private static void verificarChavesPixConta(List<ChavePix> chavesPix)
    {
        if (chavesPix.isEmpty())
            throw new BusinessException("Nenhuma chave pix encontrada");
    }
    private static ChavePix encontrarChavePix(List<ChavePix> chavesPix, ChavePixForm chavePixForm)
    {
        for(ChavePix chavePix : chavesPix) if (chavePix.getTipoChave().equals(chavePixForm.tipoChave())) return chavePix;
        throw new BusinessException("Chave pix não encontrada");
    }

}
