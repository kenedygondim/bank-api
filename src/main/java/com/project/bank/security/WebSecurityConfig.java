package com.project.bank.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig
{
    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        return httpSecurity
                .csrf(csrf -> csrf.disable()) //a proteção csrf não precisa estar ativada em autenticações de API's REST baseadas em token
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //ativando autenticação stateless (baseada em tokens), sem guardar estado de sessões anteriores
                .authorizeHttpRequests(autorize -> autorize
                        .requestMatchers(HttpMethod.POST, "/bank/clientes/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/bank/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/bank/clientes").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "bank/conta/aprovarConta/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "bank/conta/reprovarConta").hasRole("admin")
                        .requestMatchers(HttpMethod.POST, "/bank/endereco").hasRole("user")
                        .requestMatchers(HttpMethod.GET, "/bank/endereco/{clienteId}").hasRole("user")
                        .requestMatchers(HttpMethod.GET, "/bank/clientes").hasRole("admin")
                        .anyRequest().authenticated()
                )//quais endpoints serão autorizados
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) //antes de bloquear uma requisição, verificar o token
                .build();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception
    {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}