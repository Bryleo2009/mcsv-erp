package com.mcsv.usuario_service.Repo;

import com.mcsv.usuario_service.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepo extends JpaRepository<Usuario, Integer> {
    Usuario findByEmail(String email);
}
