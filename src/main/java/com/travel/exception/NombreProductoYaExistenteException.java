package com.travel.exception;

public class NombreProductoYaExistenteException extends RuntimeException {
    public NombreProductoYaExistenteException(String message) {
        super(message);
    }
}

