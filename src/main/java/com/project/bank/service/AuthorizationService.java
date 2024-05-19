package com.project.bank.service;

import com.project.bank.repository.AcessoContaRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizationService implements UserDetailsService
{
    private final AcessoContaRepository acessoContaRepository;
    @Override
    public UserDetails loadUserByUsername(String login)
    {
        if(acessoContaRepository.findByLogin(login) == null)
            throw new UsernameNotFoundException("Usuário não encontrado");

        System.out.println("Usuário encontrado");
        return acessoContaRepository.findByLogin(login);
    }
}
