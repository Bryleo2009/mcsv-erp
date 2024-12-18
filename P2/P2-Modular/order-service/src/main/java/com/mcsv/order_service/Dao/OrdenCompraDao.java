package com.mcsv.order_service.Dao;

import com.mcsv.inventario_service.Dto.InventarioDto;
import com.mcsv.order_service.Config.Exception.ExceptionApp;
import com.mcsv.order_service.Dto.OrdenCompraDto;
import com.mcsv.order_service.Event.OrderEvent;
import com.mcsv.order_service.Model.OrdenCompra;
import com.mcsv.order_service.Model.OrdenCompraDetalle;
import com.mcsv.order_service.Repo.IOrdenCompraRepo;
import com.mcsv.order_service.Service.External.IInventarioService;
import com.mcsv.order_service.Service.IOrdenCompraService;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrdenCompraDao implements IOrdenCompraService {

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Autowired
    private IOrdenCompraRepo repo;

    @Autowired
    private IInventarioService inventarioService;

    @Autowired
    private Tracer tracer;


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
        return repo.findById(id).orElseThrow(() -> new ExceptionApp("OrdenCompra no encontrado"));
    }

    @Override
    @Transactional
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
            inventarioDtos.add(InventarioDto.builder().
                    codigoSKU(detalle.getCodigoSKU()).
                    stock(detalle.getCantidad()).
                    build());
        }

        // Obtener el span actual (si existe) para propagar el parentId
        Span span1 = tracer.nextSpan().name("inventarioService.findByCodigoSKU | save");


        try (Tracer.SpanInScope ws1 = tracer.withSpan(span1)) {
            // Realizamos la validación de stock
            InventarioDto[] resp = inventarioService.findByCodigoSKU(inventarioDtos).toArray(new InventarioDto[0]);

            if (resp.length == 0) {
                throw new ExceptionApp("No se encontraron productos en el inventario con dichos códigos SKU");
            }

            List<String> sinStock = List.of(resp).stream()
                    .filter(inventarioDto -> !inventarioDto.isInStock())
                    .map(InventarioDto::getCodigoSKU)
                    .toList();

            if (!sinStock.isEmpty()) {
                // Filtramos los inventarios que no tienen stock
                inventarioDtos = inventarioDtos.stream()
                        .filter(inventarioDto -> !sinStock.contains(inventarioDto.getCodigoSKU()))
                        .collect(Collectors.toList());

                if (!inventarioDtos.isEmpty()) {
                    // Procesar la disminución de stock
                    inventarioService.confirmarDisminuirStock2(inventarioDtos, true);

                }
                throw new ExceptionApp("No hay stock para los siguientes productos: " + sinStock);
            } else {
                // Si todo tiene stock, confirmamos la disminución de stock
                for (InventarioDto inventarioDto : inventarioDtos) {
                        inventarioService.confirmarDisminuirStock(inventarioDto, true);
                }
            }
        } catch (Exception e) {
            inventarioService.confirmarDisminuirStock2(inventarioDtos, false);
            throw new ExceptionApp("Error al confirmar la disminución de stock");
        } finally {
            log.info("Trace id: {}", span1.context().traceId());
            span1.end();  // Finalizamos el primer span en el bloque finally para asegurarnos de que se cierre
        }

        OrdenCompra nuevaOrdenCompra = repo.save(ordencompra);
        kafkaTemplate.send("notificationTopic", new OrderEvent(nuevaOrdenCompra.getNumeroOrden()));

        // Guarda la orden de compra junto con sus detalles en una única operación
        return nuevaOrdenCompra;
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
