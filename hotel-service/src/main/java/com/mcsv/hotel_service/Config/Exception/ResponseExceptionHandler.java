package com.mcsv.hotel_service.Config.Exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
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

    HttpStatus Status = null;

    //otros errores
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> manejarTodasExcepciones(Exception ex, WebRequest request){
        Status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detalle = obtenerLineaDeCodigo2(ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage() + " - " + detalle, request.getDescription(false),Status.toString());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, Status);
    }

    //error en el ingreso de datos
    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public final ResponseEntity<ExceptionResponse> manejarDataExcepciones(InvalidDataAccessResourceUsageException  ex, WebRequest request){
        Status = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getCause().getCause().getMessage().toString(), request.getDescription(false),Status.toString());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, Status);
    }

    //error en datos vacios
    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ExceptionResponse> manejarDataVaciaExcepciones(DataIntegrityViolationException  ex, WebRequest request){
        Status = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getCause().getCause().getMessage().toString(), request.getDescription(false),Status.toString());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, Status);
    }

    //errores de parseo
//
//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        Status = HttpStatus.INTERNAL_SERVER_ERROR;
//        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false),Status.toString());
//        return new ResponseEntity<Object>(exceptionResponse, Status);
//    }

    @ExceptionHandler(CannotCreateTransactionException.class)
    public final ResponseEntity<ExceptionResponse> manejarDataVaciaExcepciones2(CannotCreateTransactionException  ex, WebRequest request){
        Status = HttpStatus.SERVICE_UNAVAILABLE;
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getCause().getCause().getMessage().toString(), request.getDescription(false),Status.toString());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, Status);
    }

    //error 404
    //cualquier error Personalizado
    @ExceptionHandler(ModeloNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> manejarModeloExcepciones(ModeloNotFoundException ex, WebRequest request){
        Status = HttpStatus.NOT_FOUND;
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false),Status.toString());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, Status);
    }

    @ExceptionHandler(SQLTransientConnectionException.class)
    public final ResponseEntity<ExceptionResponse> handleSQLTransientConnectionException(SQLTransientConnectionException ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detalle = obtenerLineaDeCodigo2(ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage() + " - " + detalle, request.getDescription(false), status.toString());
        return new ResponseEntity<>(exceptionResponse, status);
    }

    //error al validar tamaño minimo de cadena
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        Status = HttpStatus.INTERNAL_SERVER_ERROR;
//        String errores = "";
//        for(ObjectError e : ex.getBindingResult().getAllErrors()) {
//            errores += e.getDefaultMessage() + " en el modelo " + e.getObjectName() + " --- ";
//        }
//        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Error en los campos: " + errores, request.getDescription(false),Status.toString());
//        return new ResponseEntity<Object>(exceptionResponse, Status);
//    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<Object> manejarNullPointerException(NullPointerException ex,WebRequest request) {
        String detalle = obtenerLineaDeCodigo(ex);
        Status = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Se ha producido un error debido a un objeto nulo - Servidor: " + detalle, request.getDescription(false),Status.toString());
        return new ResponseEntity<Object>(exceptionResponse, Status);
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