package com.treeaxes.Orion.Controller;

import com.treeaxes.Orion.DTO.AuthResponse;
import com.treeaxes.Orion.DTO.UsuarioRequest;
import com.treeaxes.Orion.Exception.ResourceNotFoundException;
import com.treeaxes.Orion.Model.Usuario;
import com.treeaxes.Orion.Repository.UsuarioRepository;
import com.treeaxes.Orion.Security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UsuarioRequest u) {
        log.info("POST /api/auth/login - Intento de inicio de sesion para usuario: {}", u.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword())
        );

        Usuario usuario = usuarioRepository.findByUsername(u.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        final String jwt = jwtUtil.generateToken(usuario);
        log.info("Inicio de sesion exitoso para Usuario id={}", usuario.getId());
        return ResponseEntity.ok(new AuthResponse(usuario.getId(), jwt));
    }

}
