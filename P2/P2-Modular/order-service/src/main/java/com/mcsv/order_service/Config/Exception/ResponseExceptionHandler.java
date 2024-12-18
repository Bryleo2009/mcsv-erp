package com.mcsv.order_service.Config.Exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcsv.inventario_service.Config.Exception.ExceptionApp;
import com.mcsv.inventario_service.Config.Exception.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLTransientConnectionException;
import java.util.Date;
import java.util.UUID;

@ControllerAdvice
@RestController
@Slf4j
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler{


    HttpStatus status = null;

    // Manejo de errores
    @ExceptionHandler(ExceptionApp.class)
    public final ResponseEntity<ExceptionResponse> manejarModeloExcepciones(ExceptionApp ex, WebRequest request) {
        HttpStatus status = ex.getHttpStatus();

        // Construye el ExceptionResponse incluyendo el requestId y el HttpStatus
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .timestamp(new Date())
                .mensaje(ex.getMessage())
                .fallo_en(request.getDescription(false))
                .status(status.toString())
                .requestId(UUID.randomUUID().toString())
                .build();

        log.error("Error: " + exceptionResponse.toString());
        return new ResponseEntity<>(exceptionResponse, status);
    }

    // Manejo de errores genéricos
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> manejarTodasExcepciones(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detalle = obtenerLineaDeCodigo2(ex);

        // Crear la respuesta de la excepción con los datos básicos
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .status(status.toString())
                .fallo_en(request.getDescription(false))
                .timestamp(new Date())
                .mensaje(ex.getMessage() + " - " + detalle)
                .requestId(UUID.randomUUID().toString())
                .build();

        // Obtener el mensaje
        String mensaje = exceptionResponse.getMensaje();

        // Verificar si el mensaje contiene un JSON en su interior
        if (mensaje != null && mensaje.contains("[{") && mensaje.contains("}]")) {
            try {
                // Intentar extraer el JSON contenido en el mensaje
                int startIndex = mensaje.indexOf("[{");
                int endIndex = mensaje.lastIndexOf("}]");

                // Si encontramos la porción JSON, intentamos parsearlo
                if (startIndex != -1 && endIndex != -1) {
                    String jsonPart = mensaje.substring(startIndex, endIndex + 2);  // Obtener la cadena JSON

                    // Usamos ObjectMapper para parsear el JSON
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(jsonPart);  // Parsear la parte del JSON

                    // Verificar si existe un campo "mensaje" en el JSON y extraerlo
                    if (rootNode.isArray() && rootNode.size() > 0) {
                        JsonNode jsonNode = rootNode.get(0);  // Obtener el primer objeto del array
                        if (jsonNode.has("mensaje")) {
                            String subMensaje = jsonNode.get("mensaje").asText();
                            exceptionResponse.setMensaje(subMensaje);  // Establecer solo el submensaje
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Error al procesar el mensaje JSON: {}", e.getMessage());
            }
        }

        // Loguear el error
        log.error("Error: " + exceptionResponse.toString());

        // Devolver la respuesta con el submensaje procesado
        return new ResponseEntity<>(exceptionResponse, status);
    }

    // Manejo de errores de acceso a datos
    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public final ResponseEntity<ExceptionResponse> manejarDataExcepciones(InvalidDataAccessResourceUsageException ex, WebRequest request) {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detalle = obtenerLineaDeCodigo2(ex);
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .status(status.toString())
                .fallo_en(request.getDescription(false))
                .timestamp(new Date())
                .mensaje(ex.getMessage() + " - " + detalle)
                .requestId(UUID.randomUUID().toString())
                .build();
        log.error("Error: " + exceptionResponse.toString());
        return new ResponseEntity<>(exceptionResponse, status);
    }

    // Manejo de errores de datos vacíos
    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ExceptionResponse> manejarDataVaciaExcepciones(DataIntegrityViolationException ex, WebRequest request) {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detalle = obtenerLineaDeCodigo2(ex);
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .status(status.toString())
                .fallo_en(request.getDescription(false))
                .timestamp(new Date())
                .mensaje(ex.getMessage() + " - " + detalle)
                .requestId(UUID.randomUUID().toString())
                .build();
        log.error("Error: " + exceptionResponse.toString());
        return new ResponseEntity<>(exceptionResponse, status);
    }

    // Manejo de excepciones de transacciones
    @ExceptionHandler(CannotCreateTransactionException.class)
    public final ResponseEntity<ExceptionResponse> manejarDataVaciaExcepciones2(CannotCreateTransactionException ex, WebRequest request) {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        //ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getCause().getCause().getMessage(), request.getDescription(false), status.toString());
        String detalle = obtenerLineaDeCodigo2(ex);
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .status(status.toString())
                .fallo_en(request.getDescription(false))
                .timestamp(new Date())
                .mensaje(ex.getMessage() + " - " + detalle)
                .requestId(UUID.randomUUID().toString())
                .build();
        log.error("Error: " + exceptionResponse.toString());
        return new ResponseEntity<>(exceptionResponse, status);
    }

    // Manejo de excepciones SQL
    @ExceptionHandler(SQLTransientConnectionException.class)
    public final ResponseEntity<ExceptionResponse> handleSQLTransientConnectionException(SQLTransientConnectionException ex, WebRequest request) {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detalle = obtenerLineaDeCodigo2(ex);
        //ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage() + " - " + detalle, request.getDescription(false), status.toString());
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .status(status.toString())
                .fallo_en(request.getDescription(false))
                .timestamp(new Date())
                .mensaje(ex.getMessage() + " - " + detalle)
                .requestId(UUID.randomUUID().toString())
                .build();
        log.error("Error: " + exceptionResponse.toString());
        return new ResponseEntity<>(exceptionResponse, status);
    }

    // Manejo de errores de lectura de mensajes no legibles
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,HttpHeaders headers,HttpStatusCode status,WebRequest request) {

        // Cambia el estado a BAD_REQUEST para representar que el mensaje no es legible
        String detalle = obtenerLineaDeCodigo2(ex);
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .status(status.toString())
                .fallo_en(request.getDescription(false))
                .timestamp(new Date())
                .mensaje(ex.getMessage() + " - " + detalle)
                .requestId(UUID.randomUUID().toString())
                .build();
        log.error("Error: " + exceptionResponse.toString());
        // Retorna la respuesta
        return new ResponseEntity<>(exceptionResponse, status);
    }

    // Manejo de errores de validación de argumentos
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpHeaders headers,HttpStatusCode status,WebRequest request) {

        // Crear una cadena de errores detallada
        StringBuilder errores = new StringBuilder();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            errores.append(error.getDefaultMessage())
                    .append(" en el modelo ")
                    .append(error.getObjectName())
                    .append(" --- ");
        }

        // Crear la respuesta de excepción personalizada
        String detalle = obtenerLineaDeCodigo2(ex);

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .status(status.toString())
                .fallo_en(request.getDescription(false))
                .timestamp(new Date())
                .mensaje("Error en los campos: " + errores.toString() + " - " + detalle)
                .requestId(UUID.randomUUID().toString())
                .build();
        log.error("Error: " + exceptionResponse.toString());
        return new ResponseEntity<>(exceptionResponse, status);
    }

    // Manejo de NullPointerException
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> manejarNullPointerException(NullPointerException ex, WebRequest request) {
        String detalle = obtenerLineaDeCodigo(ex);
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .status(status.toString())
                .fallo_en(request.getDescription(false))
                .timestamp(new Date())
                .mensaje("Se ha producido un error debido a un objeto nulo - Servidor: " + detalle)
                .requestId(UUID.randomUUID().toString())
                .build();
        log.error("Error: " + exceptionResponse.toString());
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
        String paqueteBase = this.getClass().getPackageName();

        StackTraceElement[] stackTrace = ex.getStackTrace();
        if (stackTrace != null) {
            for (StackTraceElement element : stackTrace) {
                if (element.getClassName().startsWith(paqueteBase)) {
                    return element.toString();
                }
            }
        }
        return "";
    }

}