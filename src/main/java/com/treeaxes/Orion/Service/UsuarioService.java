package com.treeaxes.Orion.Service;

import com.treeaxes.Orion.DTO.UsuarioRequest;
import com.treeaxes.Orion.DTO.UsuarioResponse;
import com.treeaxes.Orion.Exception.ResourceAlreadyExistsException;
import com.treeaxes.Orion.Exception.ResourceNotFoundException;
import com.treeaxes.Orion.Model.Usuario;
import com.treeaxes.Orion.Repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Metodo para agregar un nuevo usuario
    public UsuarioResponse agregarUsuario(UsuarioRequest u){
        log.info("Iniciando registro de usuario {}", u.getUsername());

        // Validar si el usuario ya existe para evitar duplicaciones.
        if(usuarioRepository.existsByUsername(u.getUsername())){
            log.warn("Registro rechazado: el usuario '{}' ya existe.", u.getUsername());
            throw new ResourceAlreadyExistsException("El nombre de Usuario: " + u.getUsername() + " Ya esta registrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(u.getUsername());
        usuario.setPassword(passwordEncoder.encode(u.getPassword()));
        usuarioRepository.save(usuario);

        UsuarioResponse res = new UsuarioResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getCreatedAt()
        );
        return res;
    }

    // Metodo para obtener un usuario por su ID
    public UsuarioResponse obtenerUsuario(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if(usuario == null) throw new ResourceNotFoundException("El usuario con ID '" + id + "' no existe.");

        UsuarioResponse res = new UsuarioResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getCreatedAt()
        );
        return res;
    }

    // Metodo para obtener todos los usuarios
    public List<UsuarioResponse> listarUsuarios(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> new UsuarioResponse(
                        usuario.getId(),
                        usuario.getUsername(),
                        usuario.getPassword(),
                        usuario.getCreatedAt()
                ))
                .toList();
    }

    // Metodo para eliminar un usuario por su ID
    public UsuarioResponse eliminarUsuario(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if(usuario == null) throw new ResourceNotFoundException("El usuario con ID '" + id + "' no existe.");

        usuarioRepository.delete(usuario);
        UsuarioResponse res = new UsuarioResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getCreatedAt()
        );
        return res;
    }
}
