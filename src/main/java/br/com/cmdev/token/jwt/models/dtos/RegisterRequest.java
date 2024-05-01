package br.com.cmdev.token.jwt.models.dtos;

import br.com.cmdev.token.jwt.models.UserRole;

public record RegisterRequest(String name, String email, String password, UserRole role) {
}
