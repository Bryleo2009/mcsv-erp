package com.mcsv.usuario_service.Controller;

import com.mcsv.usuario_service.Dao.UsuarioDao;
import com.mcsv.usuario_service.Model.Usuario;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@Slf4j
public class UsuarioController {
    @Autowired
    private UsuarioDao usuarioDao;

    @PostMapping
    public ResponseEntity<?> saveUsuario(@RequestBody Usuario request) {
        Usuario usuario = usuarioDao.saveUsuario(request);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateUsuario(@RequestBody Usuario request) {
        Usuario usuario = usuarioDao.updateUsuario(request);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUsuario(@RequestBody Usuario request) {
        usuarioDao.deleteUsuario(request.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUsuarioByEmail(@PathVariable String email) {
        Usuario usuario = usuarioDao.getUsuarioByEmail(email);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsuarios() {
        return new ResponseEntity<>(usuarioDao.getAllUsuarios(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    //@CircuitBreaker(name = "ratingUsuarioBraker", fallbackMethod = "ratingUsuarioFallback") //en caso de que el servicio falle
    @Retry(name = "ratingUsuarioBraker" , fallbackMethod = "ratingUsuarioFallback") //para reintentar la peticion n veces
    public ResponseEntity<?> getUsuario(@PathVariable int id) {
        Usuario usuario = usuarioDao.getUsuario(id); // Lógica para obtener el usuario
        return new ResponseEntity<>(usuario, HttpStatus.OK); // Respuesta exitosa
    }

    public ResponseEntity<?> ratingUsuarioFallback(int usuarioId, Throwable throwable) {
        log.warn("Respaldo activado por falla en el servicio"); // Mensaje de advertencia
        Usuario usuario = new Usuario(); // Crear un objeto vacío
        return new ResponseEntity<>(usuario, HttpStatus.SERVICE_UNAVAILABLE); // Devolver respuesta de servicio no disponible
    }

}
