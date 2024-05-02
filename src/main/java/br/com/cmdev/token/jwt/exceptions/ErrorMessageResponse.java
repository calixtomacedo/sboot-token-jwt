package br.com.cmdev.token.jwt.exceptions;

import org.springframework.http.HttpStatus;

public record ErrorMessageResponse(HttpStatus status, String exception, String message) {}
