package com.project.bank.service.implementation;

import com.project.bank.entity.dto.PixKeyDto;
import com.project.bank.entity.model.Account;
import com.project.bank.entity.model.PixKey;
import com.project.bank.enumeration.KeyTypeEnum;
import com.project.bank.handler.BusinessException;
import com.project.bank.handler.NotFoundException;
import com.project.bank.repository.PixKeyRepository;
import com.project.bank.service.repository.PixKeyRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class PixKeyService implements PixKeyRepositoryService {
    private final PixKeyRepository pixKeyRepository;
    private final AccountService accountService;

    @Autowired
    public PixKeyService(PixKeyRepository pixKeyRepository, AccountService accountService) {
        this.pixKeyRepository = pixKeyRepository;
        this.accountService = accountService;
    }

    @Override
    public PixKey createPixKey(PixKeyDto pixKeyDto, String cpf) {
        CompletableFuture<List<PixKey>> pixKeysAsync = CompletableFuture.supplyAsync(
                () -> this.getAllPixKeys(cpf)
        );

        Account account = accountService.getClientAccount(cpf);
        CompletableFuture<String> generatedPixKey = generatePixKey(account, pixKeyDto.keyType());
        try {
            for (PixKey pixKey : pixKeysAsync.get())
                if (pixKey.getKeyType().equals(pixKeyDto.keyType()))
                    throw new BusinessException("Você já cadastrou uma chave pix para este tipo");

            PixKey newPixKey = new PixKey();
            newPixKey.setKeyType(pixKeyDto.keyType());
            newPixKey.setAccount(account);
            newPixKey.setKeyValue(generatedPixKey.join());
            return this.savePixKey(newPixKey);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PixKey> getAllPixKeys(String cpf) {
        return pixKeyRepository.findAllKeysByCpf(cpf);
    }

    @Override
    public String deletePixKey(PixKeyDto pixKeyDto, String cpf) {
        List<PixKey> pixKeys = pixKeyRepository.findAllKeysByCpf(cpf);
        for (PixKey pixKey : pixKeys)
            if (pixKey.getKeyType().equals(pixKeyDto.keyType())) {
                pixKeyRepository.delete(pixKey);
                return "Chave PIX excluída com sucesso!";
            }
        throw new BusinessException("Chave pix não encontrada");
    }

    @Override
    public PixKey getPixKey(String chave) {
        return pixKeyRepository.findByKeyValue(chave).orElseThrow(
                () -> new NotFoundException("chave pix", chave)
        );
    }

    @Override
    public PixKey savePixKey(PixKey pixKey) {
        return pixKeyRepository.save(pixKey);
    }



    private static CompletableFuture<String> generatePixKey(Account account, KeyTypeEnum keyTypeEnum) {
        return CompletableFuture.supplyAsync(() -> switch (keyTypeEnum) {
            case CPF -> account.getClient().getCpf();
            case EMAIL -> account.getClient().getEmail();
            case PHONE_NUMBER -> account.getClient().getPhoneNumber();
            case RANDOM -> generatePixKeyRandomType();
            default -> throw new IllegalArgumentException("Unexpected value: " + keyTypeEnum);
        });
    }



    private static String generatePixKeyRandomType() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        List<Integer> separatorPositions = List.of(10, 15, 20, 25);
        for (int i = 0; i < 36; i++) {
            if (separatorPositions.contains(i)) stringBuilder.append("-");
            else if (i % 3 == 0) stringBuilder.append((char) (random.nextInt(26) + 'a'));
            else stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

}
