package com.mcsv.order_service.Controller;

import com.mcsv.order_service.Config.Exception.ModeloNotFoundException;
import com.mcsv.order_service.Dao.OrdenCompraDao;
import com.mcsv.order_service.Dto.OrdenCompraDto;
import com.mcsv.order_service.Model.OrdenCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/ordencompra")
public class OrdenCompraController {
    @Autowired
    private OrdenCompraDao ordencompraDao;

    @PostMapping
    public ResponseEntity<?> saveOrdenCompra(@RequestBody OrdenCompraDto request) {
        OrdenCompra ordencompra = ordencompraDao.save(request);
        return new ResponseEntity<>(ordencompra, HttpStatus.CREATED);
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
            throw new ModeloNotFoundException("OrdenCompra no encontrado");
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
            throw new ModeloNotFoundException("OrdenCompra no encontrado");
        }
        ordencompraDao.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
