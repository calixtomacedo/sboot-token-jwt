package br.com.cmdev.token.jwt.exceptions;

public class JwtLoginAllExceptions extends RuntimeException {

    public JwtLoginAllExceptions() {
    }

    public JwtLoginAllExceptions(String message) {
        super(message);
    }

    public JwtLoginAllExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtLoginAllExceptions(Throwable cause) {
        super(cause);
    }

    public JwtLoginAllExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
