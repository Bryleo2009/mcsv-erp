package com.mcsv.order_service.Controller;

import com.mcsv.order_service.Config.Exception.ModeloNotFoundException;
import com.mcsv.order_service.Dao.OrdenCompraDetalleDao;
import com.mcsv.order_service.Dto.OrdenCompraDetalleDto;
import com.mcsv.order_service.Model.OrdenCompraDetalle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/ordencompradetalle")
public class OrdenCompraDetalleController {
    @Autowired
    private OrdenCompraDetalleDao ordencompradetalleDao;

    @PostMapping
    public ResponseEntity<?> saveOrdenCompraDetalle(@RequestBody OrdenCompraDetalleDto request) {
        OrdenCompraDetalle ordencompradetalle = ordencompradetalleDao.save(request.getOrdenCompraDetalle());
        return new ResponseEntity<>(ordencompradetalle, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateOrdenCompraDetalle(@RequestBody OrdenCompraDetalleDto request) {
        OrdenCompraDetalle ordencompradetalle = ordencompradetalleDao.update(request.getOrdenCompraDetalle());
        return new ResponseEntity<>(ordencompradetalle, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrdenCompraDetalle(@PathVariable Long id) {
        OrdenCompraDetalle ordencompradetalle = ordencompradetalleDao.findById(id);
        if (Objects.isNull(ordencompradetalle)) {
            throw new ModeloNotFoundException("OrdenCompraDetalle no encontrado");
        }
        return new ResponseEntity<>( ordencompradetalle, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllOrdenCompraDetalles() {
        return new ResponseEntity<>( ordencompradetalleDao.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrdenCompraDetalle(@PathVariable Long id) {
        if (Objects.isNull(ordencompradetalleDao.findById(id))) {
            throw new ModeloNotFoundException("OrdenCompraDetalle no encontrado");
        }
        ordencompradetalleDao.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
