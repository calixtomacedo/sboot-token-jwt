package br.com.cmdev.token.jwt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class TokenJwtExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<ErrorMessageResponse> tokenJwtRuntimeExceptionsHandler(RuntimeException exception) {
        var classError = Arrays.stream(exception.getStackTrace()).findFirst();
        StringBuilder builderMessage = new StringBuilder();
        builderMessage.append(exception.getClass().getCanonicalName());
        builderMessage.append(" occurred in the class: ".concat(classError.get().getClassName()));
        builderMessage.append(" Method: ".concat(classError.get().getMethodName()));
        builderMessage.append(" Number line: ".concat(String.valueOf(classError.get().getLineNumber())));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR,  exception.getMessage(), builderMessage.toString()));
    }

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity<ErrorMessageResponse> generateTokenExceptionsHandler(AuthenticationException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessageResponse(HttpStatus.FORBIDDEN,  exception.getMessage(), "Incorrect email or password."));
    }

}