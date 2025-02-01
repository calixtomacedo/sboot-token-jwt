package br.com.cmdev.token.jwt.models.dtos;

public record TokenResponse(String tokenJwt, String email, String issuedAt, String expiresAt) {
}
