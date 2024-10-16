package com.mcsv.usuario_service.Dao;

import com.mcsv.usuario_service.Config.Exception.ModeloNotFoundException;
import com.mcsv.usuario_service.Model.Usuario;
import com.mcsv.usuario_service.Repo.IUsuarioRepo;
import com.mcsv.usuario_service.Service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioDao implements IUsuarioService {
    @Autowired
    private IUsuarioRepo repo;

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        return repo.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Usuario usuario) {
        return repo.save(usuario);
    }

    @Override
    public void deleteUsuario(Usuario usuario) {
        repo.delete(usuario);
    }

    @Override
    public Usuario getUsuario(int id) {
        return repo.findById(id).orElseThrow(() -> new ModeloNotFoundException("Usuario no encontrado"));
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
