package com.mcsv.calificacion_service.Controller;

import com.mcsv.calificacion_service.Dao.CalificacionDao;
import com.mcsv.calificacion_service.Model.Calificacion;
import com.mcsv.calificacion_service.Service.ICalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calificacion")
public class CalificacionController {
    @Autowired
    private CalificacionDao calificacionDao;

    @PostMapping
    public ResponseEntity<?> saveCalificacion(@RequestBody Calificacion request) {
        Calificacion calificacion = calificacionDao.saveCalificacion(request);
        return new ResponseEntity<>(calificacion, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateCalificacion(@RequestBody Calificacion request) {
        Calificacion calificacion = calificacionDao.updateCalificacion(request);
        return new ResponseEntity<>(calificacion, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCalificacion(@RequestBody Calificacion request) {
        calificacionDao.deleteCalificacion(request.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCalificacion(@PathVariable String id) {
        Calificacion calificacion = calificacionDao.getCalificacionById(id);
        return new ResponseEntity<>(calificacion, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllCalificaciones() {
        return new ResponseEntity<>(calificacionDao.getCalificaciones(), HttpStatus.OK);
    }

    @GetMapping("/hotel/{id}")
    public ResponseEntity<?> getCalificacionesByHotel(@PathVariable int id) {
        return new ResponseEntity<>(calificacionDao.getCalificacionByIdHotel(id), HttpStatus.OK);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> getCalificacionesByUsuario(@PathVariable int id) {
        return new ResponseEntity<>(calificacionDao.getCalificacionByIdUsuario(id), HttpStatus.OK);
    }

}
