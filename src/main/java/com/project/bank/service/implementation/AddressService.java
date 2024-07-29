package com.project.bank.service.implementation;

import com.project.bank.entity.dto.AddressDto;
import com.project.bank.entity.model.Address;
import com.project.bank.entity.model.Client;
import com.project.bank.feign.AddressFeign;
import com.project.bank.feign.response.AddressResponse;
import com.project.bank.handler.BusinessException;
import com.project.bank.handler.NotFoundException;
import com.project.bank.repository.AddressRepository;
import com.project.bank.service.repository.AddressRepositoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AddressService implements AddressRepositoryService {
    private AddressRepository addressRepository;
    private AddressFeign addressFeign;
    private ClientService clientService;

    @Autowired
    public AddressService(AddressRepository addressRepository, AddressFeign addressFeign, ClientService clientService) {
        this.addressRepository = addressRepository;
        this.addressFeign = addressFeign;
        this.clientService = clientService;
    }

    @Override
    public Address createAddress(AddressDto addressDto, String cpf) {
        Client client = clientService.getUser(cpf);
        if (client.getAddress() != null)
            throw new BusinessException("O usuario já possui um endereço cadastrado.");
        Optional<AddressResponse> addressResponse = addressFeign.getAddressByPostalCode(addressDto.cep());
        Address address = createUserAddressObject(validatePostalCode(addressResponse), addressDto, client);
        return this.saveAddress(address);
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address getAddressByCpf(String cpf) {
        Address address = clientService.getUser(cpf).getAddress();
        if (address == null)
            throw new NotFoundException("endereço", cpf);
        return address;
    }

    @Override
    public Address updateAddress(AddressDto addressDto) {
        return null;
    }

    private static AddressResponse validatePostalCode(Optional<AddressResponse> addressResponse) {
        if (addressResponse.isPresent())
            return new ModelMapper().map(addressResponse, AddressResponse.class);
        throw new BusinessException("CEP não encontrado");
    }

    private static Address createUserAddressObject(AddressResponse addressResponse, AddressDto addressDto, Client client) {
        return Address.builder()
                .postalCode(addressResponse.getCep())
                .stateAbbr(addressResponse.getUf())
                .city(addressResponse.getLocalidade())
                .neighborhood(addressResponse.getBairro())
                .street(addressResponse.getLogradouro())
                .houseNumber(addressDto.number())
                .complement(addressDto.complement())
                .client(client)
                .build();
    }
}
