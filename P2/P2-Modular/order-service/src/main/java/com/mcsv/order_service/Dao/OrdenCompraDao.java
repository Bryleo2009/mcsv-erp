package com.mcsv.order_service.Dao;

import com.mcsv.inventario_service.Dto.InventarioDto;
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
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrdenCompraDao implements IOrdenCompraService {

    @Autowired
    private IOrdenCompraRepo repo;

    @Autowired
    private WebClient.Builder webClientBuilder;

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

        List<InventarioDto> inventarioDtos = new ArrayList<>();
        for (OrdenCompraDetalle detalle : detalles) {
            new InventarioDto();
            inventarioDtos.add(InventarioDto.builder().
                    codigoSKU(detalle.getCodigoSKU()).
                    cantidad(detalle.getCantidad()).
                    build());
        }


        InventarioDto[] resp = webClientBuilder.build().post()
                .uri("http://inventario-service/inventario/findByCodigoSKU")
                .bodyValue(inventarioDtos) // Aquí envías la lista de códigos SKU
                .retrieve()
                .bodyToMono(InventarioDto[].class)
                .block();

        System.out.println("InventarioDtos: " + Arrays.toString(resp));

        if (resp.length == 0) {
            throw new ModeloNotFoundException("No se encontraron productos en el inventario con dichos códigos SKU");
        }

        List<String> sinStock = List.of(resp).stream()
                .filter(inventarioDto -> !inventarioDto.isInStock())
                .map(InventarioDto::getCodigoSKU)
                .toList();

        System.out.println("Sin stock: " + sinStock);

        if (!sinStock.isEmpty()) {
            throw new ModeloNotFoundException("No hay stock para los siguientes productos: " + sinStock);
        }

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
