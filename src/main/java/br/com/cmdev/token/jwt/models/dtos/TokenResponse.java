package br.com.cmdev.token.jwt.models.dtos;

import java.util.Date;

public record TokenResponse(String tokenJwt, String email, String issuedAt, String expiresAt) {
}
