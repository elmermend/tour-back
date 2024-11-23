package com.travel.exception;

public class UserAlreadyExistException extends Exception {

    public UserAlreadyExistException() {
        super();
    }

    public UserAlreadyExistException(String mensaje) {
        super(mensaje);
    }
}