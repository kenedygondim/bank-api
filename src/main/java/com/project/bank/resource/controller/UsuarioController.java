package com.project.bank.resource.controller;

import com.project.bank.entity.form.RegisterForm;
import com.project.bank.entity.model.Usuario;
import com.project.bank.service.implementation.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bank/usuarios")
@RequiredArgsConstructor
public class UsuarioController
{
    private final UsuarioService usuarioService;

    @PostMapping("/auth/register")
    public ResponseEntity<String> solicitarConta(@RequestBody @Valid RegisterForm formUsuario)
    {
        return ResponseEntity.ok("Conta solicitada com sucesso! Número da solicitação: " +  usuarioService.solicitarConta(formUsuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obterUsuario(@PathVariable("id") String id)
    {
        return ResponseEntity.ok(usuarioService.obterUsuario(id));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obterUsuarios()
    {
        return ResponseEntity.ok(usuarioService.obterUsuarios());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirUsuario(@PathVariable("id") String id)
    {
        return ResponseEntity.ok(usuarioService.excluirUsuario(id));
    }
}
