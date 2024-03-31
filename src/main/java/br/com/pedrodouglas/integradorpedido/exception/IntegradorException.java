package br.com.pedrodouglas.integradorpedido.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

@Getter
public class IntegradorException extends RuntimeException {

    private HttpStatus status;
    private Object retorno;

    public IntegradorException(String mensagem) {
        super(mensagem);
    }

    public IntegradorException(String mensagem, HttpStatus status) {
        super(mensagem);
        this.status = status;
    }

    IntegradorException(String mensagem, HttpStatus status, Object retorno) {
        super(mensagem);
        this.status = status;
        this.retorno = retorno;
    }

}
