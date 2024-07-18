package com.project.bank.feign;

import com.project.bank.feign.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(url = "https://viacep.com.br/ws/", name = "viacep")
public interface AddressFeign
{
    @GetMapping("{postalCode}/json")
    Optional<AddressResponse> getAddressByPostalCode(@PathVariable String postalCode);
}
