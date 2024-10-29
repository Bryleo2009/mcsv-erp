package com.mcsv.usuario_service.Config.Exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLTransientConnectionException;
import java.util.Date;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler{


    HttpStatus status = null;

    // Manejo de errores genéricos
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> manejarTodasExcepciones(Exception ex, WebRequest request) {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detalle = obtenerLineaDeCodigo2(ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage() + " - " + detalle, request.getDescription(false), status.toString());
        return new ResponseEntity<>(exceptionResponse, status);
    }

    // Manejo de errores de acceso a datos
    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public final ResponseEntity<ExceptionResponse> manejarDataExcepciones(InvalidDataAccessResourceUsageException ex, WebRequest request) {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getCause().getCause().getMessage(), request.getDescription(false), status.toString());
        return new ResponseEntity<>(exceptionResponse, status);
    }

    // Manejo de errores de datos vacíos
    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ExceptionResponse> manejarDataVaciaExcepciones(DataIntegrityViolationException ex, WebRequest request) {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getCause().getCause().getMessage(), request.getDescription(false), status.toString());
        return new ResponseEntity<>(exceptionResponse, status);
    }

    // Manejo de excepciones de transacciones
    @ExceptionHandler(CannotCreateTransactionException.class)
    public final ResponseEntity<ExceptionResponse> manejarDataVaciaExcepciones2(CannotCreateTransactionException ex, WebRequest request) {
        status = HttpStatus.SERVICE_UNAVAILABLE;
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getCause().getCause().getMessage(), request.getDescription(false), status.toString());
        return new ResponseEntity<>(exceptionResponse, status);
    }

    // Manejo de errores 404
    @ExceptionHandler(ModeloNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> manejarModeloExcepciones(ModeloNotFoundException ex, WebRequest request) {
        status = HttpStatus.NOT_FOUND;
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false), status.toString());
        return new ResponseEntity<>(exceptionResponse, status);
    }

    // Manejo de excepciones SQL
    @ExceptionHandler(SQLTransientConnectionException.class)
    public final ResponseEntity<ExceptionResponse> handleSQLTransientConnectionException(SQLTransientConnectionException ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detalle = obtenerLineaDeCodigo2(ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage() + " - " + detalle, request.getDescription(false), status.toString());
        return new ResponseEntity<>(exceptionResponse, status);
    }

    // Manejo de errores de lectura de mensajes no legibles
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        // Cambia el estado a BAD_REQUEST para representar que el mensaje no es legible
        HttpStatus errorStatus = HttpStatus.BAD_REQUEST;

        // Crear la respuesta de excepción personalizada
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                "Error al leer el cuerpo de la solicitud: " + ex.getMessage(),
                request.getDescription(false),
                errorStatus.toString()
        );

        // Retorna la respuesta
        return new ResponseEntity<>(exceptionResponse, errorStatus);
    }

    // Manejo de errores de validación de argumentos
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        HttpStatus errorStatus = HttpStatus.BAD_REQUEST;

        // Crear una cadena de errores detallada
        StringBuilder errores = new StringBuilder();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            errores.append(error.getDefaultMessage())
                    .append(" en el modelo ")
                    .append(error.getObjectName())
                    .append(" --- ");
        }

        // Crear la respuesta de excepción personalizada
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                "Error en los campos: " + errores.toString(),
                request.getDescription(false),
                errorStatus.toString()
        );

        return new ResponseEntity<>(exceptionResponse, errorStatus);
    }

    // Manejo de NullPointerException
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> manejarNullPointerException(NullPointerException ex, WebRequest request) {
        String detalle = obtenerLineaDeCodigo(ex);
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Se ha producido un error debido a un objeto nulo - Servidor: " + detalle, request.getDescription(false), status.toString());
        return new ResponseEntity<>(exceptionResponse, status);
    }

    private String obtenerLineaDeCodigo(NullPointerException ex) {
        StackTraceElement[] stackTrace = ex.getStackTrace();
        if (stackTrace != null && stackTrace.length > 0) {
            return stackTrace[0].toString();
        }
        return "";
    }

    private String obtenerLineaDeCodigo2(Exception ex) {
        StackTraceElement[] stackTrace = ex.getStackTrace();
        if (stackTrace != null) {
            for (StackTraceElement element : stackTrace) {
                if (element.getClassName().startsWith("com.ofsystem")) {
                    return element.toString();
                }
            }
        }
        return "";
    }
}