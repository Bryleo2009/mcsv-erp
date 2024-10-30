package com.mcsv.order_service.Config.Runner;

import com.mcsv.order_service.Dao.TipoEstadoDao;
import com.mcsv.order_service.Dto.TipoEstadoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private TipoEstadoDao tipoEstadoDao;

    @Override
    public void run(String... args) throws Exception {
        List<TipoEstadoDto> estadoDtos = new ArrayList<>(Arrays.asList(
                new TipoEstadoDto("PD", "Pendiente"),
                new TipoEstadoDto("CN", "Confirmado"),
                new TipoEstadoDto("PR", "Preparando"),
                new TipoEstadoDto("EN", "Enviado"),
                new TipoEstadoDto("ENT", "Entregado"),
                new TipoEstadoDto("CN", "Cancelado")
        ));
        tipoEstadoDao.saveAll(estadoDtos);
    }
}
