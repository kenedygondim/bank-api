package com.project.bank.service.implementation;

import com.project.bank.entity.model.UserPersonalInfo;
import com.project.bank.handler.NotFoundException;
import com.project.bank.repository.UserPersonalInfoRepository;
import com.project.bank.service.repository.UserPersonalInfoRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserPersonalInfoService implements UserPersonalInfoRepositoryService {
    @Autowired
    private UserPersonalInfoRepository userPersonalInfoRepository;

    @Override
    public List<UserPersonalInfo> getUsers() {
        return userPersonalInfoRepository.findAll();
    }

    @Override
    public UserPersonalInfo getUser(String cpf) {
        return userPersonalInfoRepository.findByCpf(cpf).orElseThrow(
                () -> new NotFoundException("usuário", cpf)
        );
    }
}
