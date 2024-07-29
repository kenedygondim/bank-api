package com.project.bank.service.repository;

import com.project.bank.entity.dto.AddressDto;
import com.project.bank.entity.model.Address;
import org.springframework.stereotype.Service;

@Service
public interface AddressRepositoryService {
    Address createAddress(AddressDto addressDto, String cpf);
    Address saveAddress(Address address);
    Address getAddressByCpf(String cpf);
    Address updateAddress(AddressDto endereco);
}
