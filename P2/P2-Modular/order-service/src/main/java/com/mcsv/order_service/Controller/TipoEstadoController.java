package com.mcsv.order_service.Controller;

import com.mcsv.order_service.Config.Exception.ExceptionApp;
import com.mcsv.order_service.Dao.TipoEstadoDao;
import com.mcsv.order_service.Dto.TipoEstadoDto;
import com.mcsv.order_service.Model.TipoEstado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/tipoestado")
public class TipoEstadoController {
    @Autowired
    private TipoEstadoDao tipoestadoDao;

    @PostMapping
    public ResponseEntity<?> saveTipoEstado(@RequestBody TipoEstadoDto request) {
        TipoEstado tipoestado = tipoestadoDao.save(request);
        return new ResponseEntity<>(tipoestado, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateTipoEstado(@RequestBody TipoEstadoDto request) {
        TipoEstado tipoestado = tipoestadoDao.update(request);
        return new ResponseEntity<>(tipoestado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTipoEstado(@PathVariable String id) {
        TipoEstado tipoestado = tipoestadoDao.findById(id);
        if (Objects.isNull(tipoestado)) {
            throw new ExceptionApp("TipoEstado no encontrado");
        }
        return new ResponseEntity<>( tipoestado, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllTipoEstados() {
        return new ResponseEntity<>( tipoestadoDao.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTipoEstado(@PathVariable String id) {
        if (Objects.isNull(tipoestadoDao.findById(id))) {
            throw new ExceptionApp("TipoEstado no encontrado");
        }
        tipoestadoDao.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
