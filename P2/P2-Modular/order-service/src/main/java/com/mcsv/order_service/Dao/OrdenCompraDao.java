package com.mcsv.order_service.Dao;

import com.mcsv.order_service.Config.Exception.ModeloNotFoundException;
import com.mcsv.order_service.Dto.OrdenCompraDto;
import com.mcsv.order_service.Dto.TipoEstadoDto;
import com.mcsv.order_service.Model.OrdenCompra;
import com.mcsv.order_service.Model.OrdenCompraDetalle;
import com.mcsv.order_service.Repo.IOrdenCompraRepo;
import com.mcsv.order_service.Service.IOrdenCompraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrdenCompraDao implements IOrdenCompraService {
    @Autowired
    private IOrdenCompraRepo repo;

    @Autowired
    private OrdenCompraDetalleDao ordenCompraDetalleDao;

     @Override
     public List<OrdenCompraDto> findAll() {
         log.info("Buscando todos los ordencompras");
         List<OrdenCompra> entities = repo.findAll();
         return entities.stream()
                 .map(OrdenCompraDto::setOrdenCompra)
                 .collect(Collectors.toList());
     }


    @Override
    public OrdenCompra findById(String id) {
        log.info("Buscando ordencompra por id: " + id);
        return repo.findById(id).orElseThrow(() -> new ModeloNotFoundException("OrdenCompra no encontrado"));
    }

    @Override
    public OrdenCompra save(OrdenCompraDto ordencompraDto) {
        log.info("Guardando ordencompra");

        ordencompraDto.iniciales();

        // Convierte el DTO a la entidad OrdenCompra y genera el UUID
        OrdenCompra ordencompra = ordencompraDto.getOrdenCompra();

        // Asocia cada detalle con la orden de compra antes de guardar
        List<OrdenCompraDetalle> detalles = ordencompra.getDetalle();
        detalles.forEach(detalle -> detalle.setOrdenCompra(ordencompra));

        // Guarda la orden de compra junto con sus detalles en una única operación
        return repo.save(ordencompra);
    }


    @Override
    public OrdenCompra update(OrdenCompraDto ordencompraDto) {
        log.info("Actualizando ordencompra");

        OrdenCompra ordencompra = ordencompraDto.getOrdenCompra();
        return repo.save(ordencompra);
    }

    @Override
    public void delete(String id) {
        log.info("Eliminando ordencompra por id: " + id);
        repo.deleteById(id);
    }
}
