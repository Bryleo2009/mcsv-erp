package com.mcsv.inventario_service.Controller;

import com.mcsv.inventario_service.Config.Exception.ModeloNotFoundException;
import com.mcsv.inventario_service.Dao.InventarioDao;
import com.mcsv.inventario_service.Dto.InventarioDto;
import com.mcsv.inventario_service.Model.Inventario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/inventario")
public class InventarioController {
    private static final Logger log = LoggerFactory.getLogger(InventarioController.class);
    @Autowired
    private InventarioDao inventarioDao;

    @PostMapping
    public ResponseEntity<?> saveInventario(@RequestBody InventarioDto request) {
        Inventario inventario = inventarioDao.save(request);
        return new ResponseEntity<>(inventario, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateInventario(@RequestBody InventarioDto request) {
        Inventario inventario = inventarioDao.update(request);
        return new ResponseEntity<>(inventario, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInventario(@PathVariable Long id) {
        Inventario inventario = inventarioDao.findById(id);
        if (Objects.isNull(inventario)) {
            throw new ModeloNotFoundException("Inventario no encontrado");
        }
        return new ResponseEntity<>( inventario, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllInventarios() {
        return new ResponseEntity<>( inventarioDao.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInventario(@PathVariable Long id) {
        if (Objects.isNull(inventarioDao.findById(id))) {
            throw new ModeloNotFoundException("Inventario no encontrado");
        }
        inventarioDao.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/inStock/{codigoSKU}")
    public ResponseEntity<?> inStock(@PathVariable String codigoSKU) {
        return new ResponseEntity<>(inventarioDao.inStock(codigoSKU), HttpStatus.OK);
    }

    @PostMapping("/findByCodigoSKU")
    public ResponseEntity<?> findByCodigoSKU(@RequestBody List<InventarioDto> codigosSKU) {
        return new ResponseEntity<>(inventarioDao.findByCodigoSKU(codigosSKU), HttpStatus.OK);
    }
}
