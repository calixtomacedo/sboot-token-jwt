package br.com.cmdev.token.jwt.models.dtos;

import org.springframework.http.HttpStatus;

public record TokenResponse(String tokenJwt) {
}
