package com.mcsv.hotel_service.Config.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ModeloNotFoundException extends RuntimeException{

    //excepciones personalizadas

    //por ejemplo, despues de ingresar mal una contra 3 veces
    public ModeloNotFoundException (String mensaje) {
        super (mensaje);
    }
}
