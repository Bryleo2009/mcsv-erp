package com.mcsv.order_service.Controller;


import com.mcsv.order_service.Config.Exception.ExceptionApp;
import com.mcsv.order_service.Dao.OrdenCompraDao;
import com.mcsv.order_service.Dto.OrdenCompraDto;
import com.mcsv.order_service.Model.OrdenCompra;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/ordencompra")
@Slf4j
public class OrdenCompraController {
    @Autowired
    private OrdenCompraDao ordencompraDao;

    @Autowired
    private Tracer tracer;


    @PostMapping
    @CircuitBreaker(name = "llamadaInventario", fallbackMethod = "llamadaInventarioFuncion")
    @Retry(name = "llamadaInventario")
    public ResponseEntity<CompletableFuture<OrdenCompra>> saveOrdenCompra(@RequestBody OrdenCompraDto request) {
        Span currentSpan = tracer.currentSpan();

        CompletableFuture<OrdenCompra> ordenCompraFuture = CompletableFuture.supplyAsync(() -> {
            // Aquí propagamos el contexto del span actual a la tarea asincrónica
            try (Tracer.SpanInScope ws = tracer.withSpan(currentSpan)) {
                return ordencompraDao.save(request);  // El método de servicio ahora está dentro del contexto del trace
            }
        });

        return new ResponseEntity<>(ordenCompraFuture, HttpStatus.CREATED);
    }


    // Método de Fallback para manejar la situación sin inventario-service
    public ResponseEntity<CompletableFuture<OrdenCompra>> llamadaInventarioFuncion(OrdenCompraDto request, Throwable t) {
        if (t != null) {
            log.error("Fallback ejecutado debido a: {}", t.getMessage());
        } else {
            log.error("Fallback ejecutado, pero sin detalles de la excepción");
        }
        // Devolver una respuesta adecuada en caso de fallo, como 503
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }



    @PutMapping
    public ResponseEntity<?> updateOrdenCompra(@RequestBody OrdenCompraDto request) {
        OrdenCompra ordencompra = ordencompraDao.update(request);
        return new ResponseEntity<>(ordencompra, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrdenCompra(@PathVariable String id) {
        OrdenCompra ordencompra = ordencompraDao.findById(id);
        if (Objects.isNull(ordencompra)) {
            throw new ExceptionApp("OrdenCompra no encontrado");
        }
        return new ResponseEntity<>( ordencompra, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllOrdenCompras() {
        return new ResponseEntity<>( ordencompraDao.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrdenCompra(@PathVariable String id) {
        if (Objects.isNull(ordencompraDao.findById(id))) {
            throw new ExceptionApp("OrdenCompra no encontrado");
        }
        ordencompraDao.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
