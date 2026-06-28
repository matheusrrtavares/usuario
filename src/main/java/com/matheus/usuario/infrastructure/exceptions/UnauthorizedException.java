package com.matheus.usuario.infrastructure.exceptions;

import javax.security.sasl.AuthenticationException;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message){
        super(message);
    }

    public UnauthorizedException(String message, Throwable throwable){
        super(message, throwable);
    }

}
