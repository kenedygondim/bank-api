package com.project.bank.service.repository;

import com.project.bank.entity.model.UserPersonalInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserPersonalInfoRepositoryService {
    List<UserPersonalInfo> getUsers();

    UserPersonalInfo getUser(String cpf);
}
