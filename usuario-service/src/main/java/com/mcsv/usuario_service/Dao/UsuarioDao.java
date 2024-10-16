package com.mcsv.usuario_service.Dao;

import com.mcsv.calificacion_service.Model.Calificacion;
import com.mcsv.usuario_service.Config.Exception.ModeloNotFoundException;
import com.mcsv.usuario_service.Model.Usuario;
import com.mcsv.usuario_service.Repo.IUsuarioRepo;
import com.mcsv.usuario_service.Service.External.ICalificacionService;
import com.mcsv.usuario_service.Service.IUsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UsuarioDao implements IUsuarioService {
    @Autowired
    private IUsuarioRepo repo;

    @Autowired
    private ICalificacionService calificacionService;

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        return repo.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Usuario usuario) {
        return repo.save(usuario);
    }

    @Override
    public void deleteUsuario(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public Usuario getUsuario(int id) {
        Usuario usuario = repo.findById(id).orElseThrow(() -> new ModeloNotFoundException("Usuario no encontrado"));
        List<Calificacion> calificaciones = calificacionService.getCalificacionByUsuario(id);
        usuario.setCalificaciones(calificaciones);
        return usuario;
    }

    @Override
    public Usuario getUsuarioByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return repo.findAll();
    }
}
