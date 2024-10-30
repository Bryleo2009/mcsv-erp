package com.mcsv.producto_service.Controller;

import com.mcsv.producto_service.Dao.ProductoDao;
import com.mcsv.producto_service.Dto.ProductoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoDao dao;

    @GetMapping
    public ResponseEntity<?> listar(){
        return new ResponseEntity<>(dao.findAll(), HttpStatus.OK);
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
