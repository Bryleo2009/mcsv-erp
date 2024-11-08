package com.mcsv.producto_service.Config.Exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
public class ExceptionApp extends RuntimeException{

    private final HttpStatus httpStatus;

    public ExceptionApp(String mensaje, HttpStatus httpStatus) {
        super(mensaje);
        this.httpStatus = httpStatus;
    }

    public ExceptionApp(String mensaje) {
        super(mensaje);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

}
