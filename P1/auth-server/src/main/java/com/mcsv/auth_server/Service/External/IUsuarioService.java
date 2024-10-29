package com.mcsv.auth_server.Service.External;

import com.mcsv.calificacion_service.Model.Calificacion;
import com.mcsv.usuario_service.Model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "USUARIO-SERVICE")
public interface IUsuarioService {

    String url = "/usuario";

    @GetMapping(url+"/email/{email}")
    Usuario getUsuarioByEmail(@PathVariable("email") String email);

    @PostMapping(url)
    Usuario saveUsuario(Usuario usuario);
}
