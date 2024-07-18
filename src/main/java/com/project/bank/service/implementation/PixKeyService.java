package com.project.bank.service.implementation;

import com.project.bank.entity.dto.PixKeyDto;
import com.project.bank.entity.model.BankAccountInfo;
import com.project.bank.entity.model.PixKey;
import com.project.bank.enumeration.KeyTypeEnum;
import com.project.bank.handler.BusinessException;
import com.project.bank.handler.NotFoundException;
import com.project.bank.repository.PixKeyRepository;
import com.project.bank.service.repository.PixKeyRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class PixKeyService implements PixKeyRepositoryService {
    @Autowired
    private PixKeyRepository pixKeyRepository;
    @Autowired
    private BankAccountInfoService bankAccountInfoService;

    @Override
    public PixKey createPixKey(PixKeyDto pixKeyDto, String cpf) {
        BankAccountInfo bankAccountInfo = bankAccountInfoService.getUserAccount(cpf);
        List<PixKey> pixKeys = pixKeyRepository.findAllKeysByCpf(cpf);
        for (PixKey pixKey : pixKeys)
            if (pixKey.getKeyType().equals(pixKeyDto.keyTypeEnum()))
                throw new BusinessException("Você já cadastrou uma chave pix para este tipo");
        return pixKeyRepository.save(createUserPixKeyObject(bankAccountInfo, pixKeyDto.keyTypeEnum()));
    }

    @Override
    public List<PixKey> getAllPixKeys(String cpf) {
        List<PixKey> pixKeys = pixKeyRepository.findAllKeysByCpf(cpf);
        if (pixKeys.isEmpty())
            throw new BusinessException("Nenhuma chave pix encontrada");
        return pixKeys;
    }

    @Override
    public String deletePixKey(PixKeyDto pixKeyDto, String cpf) {
        List<PixKey> pixKeys = pixKeyRepository.findAllKeysByCpf(cpf);
        for (PixKey pixKey : pixKeys)
            if (pixKey.getKeyType().equals(pixKeyDto.keyTypeEnum())) {
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

    private static PixKey createUserPixKeyObject(BankAccountInfo bankAccountInfo, KeyTypeEnum keyTypeEnum) {
        return PixKey.builder()
                .keyType(keyTypeEnum)
                .bankAccountInfo(bankAccountInfo)
                .keyValue(generatePixKey(bankAccountInfo, keyTypeEnum))
                .build();
    }

    private static String generatePixKey(BankAccountInfo bankAccountInfo, KeyTypeEnum keyTypeEnum) {
        return switch (keyTypeEnum) {
            case CPF -> bankAccountInfo.getUserPersonalInfo().getCpf();
            case EMAIL -> bankAccountInfo.getUserPersonalInfo().getEmail();
            case PHONE_NUMBER -> bankAccountInfo.getUserPersonalInfo().getPhoneNumber();
            case RANDOM -> generatePixKeyRandomType();
        };
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
