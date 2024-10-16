package com.mcsv.usuario_service.Controller;

import com.mcsv.usuario_service.Dao.UsuarioDao;
import com.mcsv.usuario_service.Model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuario(@PathVariable int id) {
        Usuario usuario = usuarioDao.getUsuario(id);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
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
}
