package com.treeaxes.Orion.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String username;
    private String password;
    private LocalDateTime createdAt;
}

