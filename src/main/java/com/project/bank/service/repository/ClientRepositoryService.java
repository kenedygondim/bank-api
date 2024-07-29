package com.project.bank.service.repository;

import com.project.bank.entity.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientRepositoryService {
    List<Client> getClients();
    Client getUser(String cpf);
    Client saveClient(Client client);
    Boolean existsByCpf(String cpf);
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phone);
}
