package com.project.bank.service.repository;

import com.project.bank.entity.dto.PixKeyDto;
import com.project.bank.entity.model.PixKey;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PixKeyRepositoryService {
    PixKey createPixKey(PixKeyDto pixKeyDto, String cpf);
    List<PixKey> getAllPixKeys(String cpf);
    PixKey getPixKey(String chave);
    String deletePixKey(PixKeyDto pixKeyDto, String cpf);
    PixKey savePixKey(PixKey pixKey);
}
