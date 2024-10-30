package com.mcsv.order_service.Service;

import com.mcsv.order_service.Dto.TipoEstadoDto;
import com.mcsv.order_service.Model.TipoEstado;

import java.util.List;

public interface ITipoEstadoService {
    List<TipoEstadoDto> findAll();
    TipoEstado findById(String id);
    TipoEstado save(TipoEstadoDto tipoestado);
    TipoEstado update(TipoEstadoDto tipoestado);
    List<TipoEstado> saveAll(List<TipoEstadoDto> tipoestadoDtos);
    void delete(String id);
}
