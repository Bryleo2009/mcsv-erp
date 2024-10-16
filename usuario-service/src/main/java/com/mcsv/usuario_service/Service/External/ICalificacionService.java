package com.mcsv.usuario_service.Service.External;

import com.mcsv.calificacion_service.Model.Calificacion;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "CALIFICACION-SERVICE")
public interface ICalificacionService {

    @GetMapping("/calificacion/usuario/{id}")
    List<Calificacion> getCalificacionByUsuario(@PathVariable("id") Integer id);

}
