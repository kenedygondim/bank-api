package com.project.bank.service;

import com.project.bank.repository.ClienteRepository;
import com.project.bank.repository.ContaRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizationService implements UserDetailsService
{
    private final ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        return clienteRepository.findByCpf(cpf);
    }
}
