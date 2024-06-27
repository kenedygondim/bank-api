package com.project.bank.resource.controller;
import com.project.bank.entity.model.Usuario;
import com.project.bank.service.implementation.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@RestController
@RequestMapping("/bank/usuarios")
@RequiredArgsConstructor
public class UsuarioController
{
    @Autowired
    private UsuarioService usuarioService;
    @GetMapping
    public ResponseEntity<Usuario> obterUsuario(Principal principal)
    {
        return ResponseEntity.ok(usuarioService.obterUsuario(principal.getName()));
    }
    @GetMapping("/all")
    public ResponseEntity<List<Usuario>> obterUsuarios()
    {
        return ResponseEntity.ok(usuarioService.obterUsuarios());
    }
}
