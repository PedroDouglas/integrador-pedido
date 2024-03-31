package br.com.pedrodouglas.integradorpedido.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorModel {

    private HttpStatus status;
    private List<String> errors;
    private String erro;
}
