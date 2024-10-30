package com.mcsv.order_service.Dao;

import com.mcsv.order_service.Config.Exception.ModeloNotFoundException;
import com.mcsv.order_service.Dto.TipoEstadoDto;
import com.mcsv.order_service.Model.TipoEstado;
import com.mcsv.order_service.Repo.ITipoEstadoRepo;
import com.mcsv.order_service.Service.ITipoEstadoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TipoEstadoDao implements ITipoEstadoService {
    @Autowired
    private ITipoEstadoRepo repo;

     @Override
     public List<TipoEstadoDto> findAll() {
         log.info("Buscando todos los tipoestados");
         List<TipoEstado> entities = repo.findAll();
         return entities.stream()
                 .map(TipoEstadoDto::setTipoEstado)
                 .collect(Collectors.toList());
     }


    @Override
    public TipoEstado findById(String id) {
        log.info("Buscando tipoestado por id: " + id);
        return repo.findById(id).orElseThrow(() -> new ModeloNotFoundException("TipoEstado no encontrado"));
    }

    @Override
    public TipoEstado save(TipoEstadoDto tipoestadoDto) {
        log.info("Guardando tipoestado");

        TipoEstado tipoestado = tipoestadoDto.getTipoEstado();
        return repo.save(tipoestado);
    }

    @Override
    public TipoEstado update(TipoEstadoDto tipoestadoDto) {
        log.info("Actualizando tipoestado");

        TipoEstado tipoestado = tipoestadoDto.getTipoEstado();
        return repo.save(tipoestado);
    }

    @Override
    public List<TipoEstado> saveAll(List<TipoEstadoDto> tipoestadoDtos) {
        return repo.saveAll(tipoestadoDtos.stream()
                .map(TipoEstadoDto::getTipoEstado)
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(String id) {
        log.info("Eliminando tipoestado por id: " + id);
        repo.deleteById(id);
    }
}
