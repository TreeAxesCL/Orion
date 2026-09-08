package com.treeaxes.Orion.Controller;

import com.treeaxes.Orion.DTO.UsuarioRequest;
import com.treeaxes.Orion.DTO.UsuarioResponse;
import com.treeaxes.Orion.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // ======= REGISTRAR USUARIOS ======
    // Endpoint para registrar un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> agregarUsuario(@RequestBody UsuarioRequest u){
        UsuarioResponse response = usuarioService.agregarUsuario(u);
        return ResponseEntity.ok(response);
    }
    // ===============================

    // ======= OBTENER USUARIOS ======
    // Endpoint para obtener un usuario por su ID
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<UsuarioResponse> obtenerUsuario(@PathVariable Long id){
        UsuarioResponse response = usuarioService.obtenerUsuario(id);
        if(response == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    // Endpoint para obtener todos los usuarios
    @GetMapping("/all")
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios(){
        List<UsuarioResponse> response = usuarioService.listarUsuarios();
        return ResponseEntity.ok(response);
    }
    // ===============================

    // ======= ELIMINAR USUARIOS ======
    // Endpoint para eliminar un usuario por su ID
    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<UsuarioResponse> eliminarUsuario(@PathVariable Long id){
        UsuarioResponse response = usuarioService.obtenerUsuario(id);
        if(response == null) return ResponseEntity.notFound().build();

        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok(response);
    }
    // ===============================

}
