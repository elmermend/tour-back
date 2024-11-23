package com.travel.exception;

public class TravelRepositoryException extends Exception {

    public TravelRepositoryException() {
        super();
    }

    public TravelRepositoryException(String mensaje) {
        super(mensaje);
    }
}