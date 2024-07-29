package com.project.bank.service.implementation;

import com.project.bank.entity.model.Client;
import com.project.bank.handler.NotFoundException;
import com.project.bank.repository.ClientRepository;
import com.project.bank.service.repository.ClientRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClientService implements ClientRepositoryService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getUser(String cpf) {
        return clientRepository.findByCpf(cpf).orElseThrow(
                () -> new NotFoundException("usuário", cpf)
        );
    }

    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Boolean existsByCpf(String cpf) {
        return clientRepository.existsByCpf(cpf);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByPhoneNumber(String phoneNumber) {
        return clientRepository.existsByPhoneNumber(phoneNumber);
    }


}
