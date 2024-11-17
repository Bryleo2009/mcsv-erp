package com.mcsv.producto_service.Controller;

import com.mcsv.producto_service.Dao.ProductoDao;
import com.mcsv.producto_service.Dto.ProductoDto;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoDao dao;

    @Autowired
    private Tracer tracer;

    @GetMapping
    public ResponseEntity<?> listar(){
        List<ProductoDto> productos = new ArrayList<>();

        //se llama a un currentSpan para obtener el span actual y que asi no se pierda la traza dentro del dao en la llamada a findAll
        Span currentSpan = tracer.currentSpan();
        try (Tracer.SpanInScope ws = tracer.withSpan(currentSpan)) {
            productos = dao.findAll();
        }
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody ProductoDto dto){
        return new ResponseEntity<>(dao.save(dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id){
        dao.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable String id){
        return new ResponseEntity<>(dao.findById(id), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> actualizar(@RequestBody ProductoDto dto){
        return new ResponseEntity<>(dao.update(dto), HttpStatus.OK);
    }
}
