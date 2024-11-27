package com.travel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NombreProductoYaExistenteException.class)
    public ResponseEntity<Map<String, String>> handleNombreProductoYaExistenteException(NombreProductoYaExistenteException ex) {
        // Crear un mapa con el mensaje de error
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());

        // Retornar el error con un código de estado 400 (Bad Request)
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
