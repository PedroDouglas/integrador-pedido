package br.com.pedrodouglas.integradorpedido.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class HandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IntegradorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorModel> handleCustomException(IntegradorException ex) {

        ErrorModel apiErrorModel = ErrorModel.builder()
                .status(ex.getStatus())
                .erro(ex.getMessage())
                .build();

        return new ResponseEntity<>(apiErrorModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(IntegradorException.class)
    public ResponseEntity<ErrorModel> handleBadRequestException(IntegradorException ex) {

        ErrorModel apiErrorModel = ErrorModel.builder()
                .status(ex.getStatus())
                .erro(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(apiErrorModel);
    }
}
