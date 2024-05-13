package com.project.bank.security;

import com.project.bank.repository.ClienteRepository;
import com.project.bank.repository.ContaRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter
{
    private final TokenService tokenService;
    private final ClienteRepository clienteRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        var token = this.recoverToken(request);

        if(token != null)
        {
            var cpf = tokenService.validateToken(token);

            System.out.println(cpf);

            UserDetails cliente = clienteRepository.findByCpf(cpf);

            System.out.println(cliente.toString());

            var authentication = new UsernamePasswordAuthenticationToken(cliente, null, cliente.getAuthorities());

            System.out.println(cliente.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println( SecurityContextHolder.getContext());
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
