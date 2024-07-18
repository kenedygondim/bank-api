package com.project.bank.service.implementation;

import com.project.bank.entity.dto.AddressDto;
import com.project.bank.entity.model.Address;
import com.project.bank.entity.model.UserPersonalInfo;
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
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserPersonalInfoService userPersonalInfoService;
    @Autowired
    private AddressFeign addressFeign;

    @Override
    public Address createAddress(AddressDto addressDto, String cpf) {

        System.out.println(addressDto.cep());

        UserPersonalInfo userPersonalInfo = userPersonalInfoService.getUser(cpf);
        if (userPersonalInfo.getAddress() != null)
            throw new BusinessException("O usuario já possui um endereço cadastrado.");
        Optional<AddressResponse> addressResponse = addressFeign.getAddressByPostalCode(addressDto.cep());

        System.out.println(addressResponse.toString());

        Address address = createUserAddressObject(validatePostalCode(addressResponse), addressDto, userPersonalInfo);
        return addressRepository.save(address);
    }

    @Override
    public Address getAddressByCpf(String cpf) {
        Address address = userPersonalInfoService.getUser(cpf).getAddress();
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

    private static Address createUserAddressObject(AddressResponse addressResponse, AddressDto addressDto, UserPersonalInfo userPersonalInfo) {
        return Address.builder()
                .postalCode(addressResponse.getCep())
                .state(addressResponse.getUf())
                .city(addressResponse.getLocalidade())
                .neighborhood(addressResponse.getBairro())
                .street(addressResponse.getLogradouro())
                .number(addressDto.number())
                .complement(addressDto.complement())
                .userPersonalInfo(userPersonalInfo)
                .build();
    }
}
