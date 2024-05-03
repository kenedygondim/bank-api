package com.project.bank.feign;

import com.project.bank.feign.response.EnderecoResponse;
import feign.Feign;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(url = "https://viacep.com.br/ws/", name = "viacep")
public interface EnderecoFeign
{
    @GetMapping("{cep}/json")
    Optional<EnderecoResponse> buscaEnderecoCep(@PathVariable String cep);
}
