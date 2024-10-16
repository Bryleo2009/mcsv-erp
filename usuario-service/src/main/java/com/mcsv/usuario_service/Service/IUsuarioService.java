package com.mcsv.usuario_service.Service;

import com.mcsv.usuario_service.Model.Usuario;

import java.util.List;

public interface IUsuarioService {
    Usuario saveUsuario(Usuario usuario);
    Usuario updateUsuario(Usuario usuario);
    void deleteUsuario(Usuario usuario);
    Usuario getUsuario(int id);
    Usuario getUsuarioByEmail(String email);
    List<Usuario> getAllUsuarios();
}
