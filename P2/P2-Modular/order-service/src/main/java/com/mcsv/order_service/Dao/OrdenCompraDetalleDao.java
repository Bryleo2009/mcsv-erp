package com.mcsv.order_service.Dao;

import com.mcsv.order_service.Config.Exception.ModeloNotFoundException;
import com.mcsv.order_service.Dto.OrdenCompraDetalleDto;
import com.mcsv.order_service.Model.OrdenCompra;
import com.mcsv.order_service.Model.OrdenCompraDetalle;
import com.mcsv.order_service.Repo.IOrdenCompraDetalleRepo;
import com.mcsv.order_service.Service.IOrdenCompraDetalleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrdenCompraDetalleDao implements IOrdenCompraDetalleService {
    @Autowired
    private IOrdenCompraDetalleRepo repo;

     @Override
     public List<OrdenCompraDetalleDto> findAll() {
         log.info("Buscando todos los ordencompradetalles");
         List<OrdenCompraDetalle> entities = repo.findAll();
         return entities.stream()
                 .map(OrdenCompraDetalleDto::setOrdenCompraDetalle)
                 .collect(Collectors.toList());
     }


    @Override
    public OrdenCompraDetalle findById(Long id) {
        log.info("Buscando ordencompradetalle por id: " + id);
        return repo.findById(id).orElseThrow(() -> new ModeloNotFoundException("OrdenCompraDetalle no encontrado"));
    }

    @Override
    public OrdenCompraDetalle save(OrdenCompraDetalle ordencompradetalle) {
        log.info("Guardando ordencompradetalle");
        return repo.save(ordencompradetalle);
    }

    @Override
    public OrdenCompraDetalle update(OrdenCompraDetalle ordencompradetalle) {
        log.info("Actualizando ordencompradetalle");
        return repo.save(ordencompradetalle);
    }

    @Override
    public void delete(Long id) {
        log.info("Eliminando ordencompradetalle por id: " + id);
        repo.deleteById(id);
    }

    @Override
    public List<OrdenCompraDetalle> saveAll(List<OrdenCompraDetalle> ordencompradetalle) {
        log.info("Guardando todos los ordencompradetalles");
        return repo.saveAll(ordencompradetalle);
    }
}
