package com.project.bank.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.bank.enumerator.SolicitacaoConta;
import com.project.bank.enumerator.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_clientes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
public class Cliente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "primeiro_nome", nullable = false, length = 25)
    private String primeiroNome;

    @Column(nullable = false, length = 25)
    private String sobrenome;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(name = "data_nascimento", nullable = false, length = 10)
    private String dataNascimento;

    @Setter
    @Column(nullable = false, length = 30, unique = true)
    private String email;

    @Setter
    @Column(nullable = false, length = 11, unique = true)
    private String numeroTelefone;

    //permite que a exclusão de um cliente remova registros de endereço, mas não o contrário
    //obs: essa config não criará uma chave estrangeira do lado do Cliente
    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Endereco endereco;

    @Column(name = "senha", nullable = false)
    private String senhaAuth;

    @Column(nullable = false)
    @Builder.Default
    private UserRole role = UserRole.USER;

    @Setter
    @Builder.Default
    private SolicitacaoConta solicitacaoConta = SolicitacaoConta.PENDENTE;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.senhaAuth;
    }

    @Override
    public String getUsername() {
        return this.cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id='" + id + '\'' +
                ", primeiroNome='" + primeiroNome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", dataNascimento='" + dataNascimento + '\'' +
                ", email='" + email + '\'' +
                ", numeroTelefone='" + numeroTelefone + '\'' +
                ", endereco=" + endereco +
                ", senhaAuth='" + senhaAuth + '\'' +
                ", solicitacaoConta=" + solicitacaoConta +
                '}';
    }
}
