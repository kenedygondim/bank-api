package com.project.bank.service.implementation;
import com.project.bank.entity.dto.ContaDto;
import com.project.bank.entity.dto.TransferenciaDto;
import com.project.bank.entity.form.TransferenciaForm;
import com.project.bank.entity.model.ChavePix;
import com.project.bank.entity.model.Conta;
import com.project.bank.entity.model.Transferencia;
import com.project.bank.enumeration.StatusConta;
import com.project.bank.enumeration.TipoConta;
import com.project.bank.handler.BusinessException;
import com.project.bank.handler.RegistroNaoEncontradoException;
import com.project.bank.repository.ChavePixRepository;
import com.project.bank.repository.ContaRepository;
import com.project.bank.repository.TransferenciaRepository;
import com.project.bank.service.repository.TransferenciaServiceRep;
import jakarta.transaction.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Service
public class TransferenciaService implements TransferenciaServiceRep
{
    @Autowired
    private TransferenciaRepository transferenciaRepository;
    @Autowired
    private ContaService contaService;
    @Autowired
    private ChavePixService chavePixService;

    @Override
    @Transactional
    public TransferenciaDto realizarTransferencia(TransferenciaForm transferenciaForm, String cpf)
    {
        ChavePix chave = chavePixService.retornarChavePix(transferenciaForm.chavePix());
        Conta contaOrigem = contaService.retornarConta(cpf);
        Conta contaDestino = chave.getConta();
        Double valorTransferencia = transferenciaForm.valor();
        String senhaTransacao = transferenciaForm.senhaTransacao();
        verificaTransferencia(contaOrigem, contaDestino, valorTransferencia, senhaTransacao);
        enviaTransferencia(contaOrigem, contaDestino, valorTransferencia);
        Transferencia transferenciaBuilder = criaObjetoTransferencia(contaOrigem, contaDestino, valorTransferencia);
        transferenciaRepository.save(transferenciaBuilder);
        return converteTransferenciaDto(transferenciaBuilder);
    }

    @Override
    public TransferenciaDto obterTransferencia(long id)
    {
        Transferencia transferencia = transferenciaRepository.findById(id).orElseThrow(
                () -> new RegistroNaoEncontradoException("chave PIX", id)
        );
        return converteTransferenciaDto(transferencia);
    }

    @Override
    public List<TransferenciaDto> obterTransferenciasCliente(String cpf)
    {
        Conta conta = contaService.retornarConta(cpf);
        return converteListaTransferenciaDto(transferenciaRepository.findAllByRemetenteIdOrDestinatarioId(conta.getId()));
    }

    private static void verificaTransferencia(Conta contaOrigem, Conta contaDestino, Double valorTransferencia, String senhaTransacao)
    {
        StatusConta statusContaDestino = contaDestino.getStatusConta();

        if(senhaTransacao.isEmpty())
            throw new BusinessException("Cadastre uma senha para transações.");
        if(valorTransferencia > contaOrigem.getSaldo())
            throw new BusinessException("Saldo insuficiente");
        if(contaDestino.equals(contaOrigem))
            throw new BusinessException("Não é possível transferir para a própria conta");;
        if(contaOrigem.getTipoConta().equals(TipoConta.POUPANCA))
            throw new BusinessException("Não é possível transferir a partir de uma conta-poupança");
        if(statusContaDestino.equals(StatusConta.BLOQUEADA) || statusContaDestino.equals(StatusConta.INATIVA))
            throw new BusinessException("Conta destino bloqueada ou inativa no momento.");
        if(!new BCryptPasswordEncoder().matches(senhaTransacao, contaOrigem.getSenhaTransacao().getSenha()))
            throw new BusinessException("Senha incorreta.");
    }
    private static void enviaTransferencia(Conta contaOrigem, Conta contaDestino, Double valorTransferencia)
    {
        contaOrigem.setSaldo(contaOrigem.getSaldo() - valorTransferencia);
        contaDestino.setSaldo(contaDestino.getSaldo() + valorTransferencia);
    }
    private static Transferencia criaObjetoTransferencia(Conta contaOrigem, Conta contaDestino, Double valorTransferencia)
    {
        return Transferencia.builder()
                .remetente(contaOrigem)
                .destinatario(contaDestino)
                .valor(valorTransferencia)
                .dataTransferencia(LocalDateTime.now())
                .build();
    }

    private static List<TransferenciaDto> converteListaTransferenciaDto(List<Transferencia> transferencias)
    {
        List<TransferenciaDto> transferenciasDto = new ArrayList<>();
        for (Transferencia transferencia : transferencias)
            transferenciasDto.add(converteTransferenciaDto(transferencia));
        return transferenciasDto;
    }
    private static TransferenciaDto converteTransferenciaDto(Transferencia transferencia)
    {
        return TransferenciaDto.builder()
                .id(transferencia.getId())
                .remetente(converteContaDto(transferencia.getRemetente()))
                .destinatario(converteContaDto(transferencia.getDestinatario()))
                .valor(transferencia.getValor())
                .dataTransferencia(transferencia.getDataTransferencia())
                .build();
    }
    private static ContaDto converteContaDto(Conta conta)
    {
        return ContaDto.builder()
                .id(conta.getId())
                .agencia(conta.getAgencia())
                .tipoConta(conta.getTipoConta())
                .statusConta(conta.getStatusConta())
                .conta(conta.getNumConta())
                .build();
    }
}
