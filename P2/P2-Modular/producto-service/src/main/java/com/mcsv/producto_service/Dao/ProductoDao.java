package com.mcsv.producto_service.Dao;

import com.mcsv.inventario_service.Dto.InventarioDto;
import com.mcsv.producto_service.Config.Exception.ExceptionApp;
import com.mcsv.producto_service.Dto.ProductoDto;
import com.mcsv.producto_service.Model.Producto;
import com.mcsv.producto_service.Repo.IProductoRepo;
import com.mcsv.producto_service.Service.External.IInventarioService;
import com.mcsv.producto_service.Service.IProductoService;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.exporter.FinishedSpan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductoDao implements IProductoService {

    @Autowired
    private IProductoRepo repo;

    @Autowired
    private IInventarioService inventarioService;

    @Autowired
    private Tracer tracer;

    @Override
    public Producto save(ProductoDto producto) {
        log.info("Guardando producto {}", producto);
        Producto obj = producto.getProducto();
        obj.setCodigoSKU("SKU"
                        + obj.getNombre().substring(0, 1).toUpperCase()  // Primer carácter del nombre, en mayúsculas
                        + (int) (Math.random() * 1000)  // Número aleatorio de 3 dígitos
                /*+ "-"
                + java.time.LocalDate.now().toString().substring(2).replace("-", "")*/ // Fecha actual en formato "YYMMDD"
        );
        new InventarioDto();
        Span span = tracer.nextSpan().name("inventarioService.saveInventario | save").start();
        try (Tracer.SpanInScope ws = tracer.withSpan(span.start())) {
            inventarioService.saveInventario(InventarioDto.builder().codigoSKU(obj.getCodigoSKU()).stock(obj.getStock()).build());
        } finally {
            log.info("Trace id: {}", span.context().traceId());
            span.end();
        }
        return repo.save(obj);
    }

    @Override
    public void delete(String id) {
        log.info("Eliminando producto con id {}", id);
        repo.deleteById(id);
    }

    @Override
    public Producto update(ProductoDto dto) {
        log.info("Actualizando producto {}", dto);
        return repo.save(dto.getProducto());
    }

    @Override
    public List<ProductoDto> findAll() {
        log.info("Buscando todos los productos");
        List<Producto> productos = repo.findAll();

        Span span = tracer.nextSpan().name("inventarioService.findByCodigoSKU | findAll");
        try (Tracer.SpanInScope ws = tracer.withSpan(span.start())) {
            for (Producto producto : productos) {
                InventarioDto inventario = inventarioService.findByCodigoSKU(List.of(
                        InventarioDto.builder()
                                .codigoSKU(producto.getCodigoSKU())
                                .stock(0)
                                .build()
                )).get(0);
                producto.setStock(inventario.getStock());
            }
        } finally {
            log.info("Trace id: {}", span.context().traceId());
            span.end();
        }

        return productos.stream().map(ProductoDto::setProducto).collect(Collectors.toList());
    }

    @Override
    public ProductoDto findById(String id) {
        log.info("Buscando producto con id {}", id);
        Producto producto = repo.findById(id).orElse(null);

        Span span = tracer.nextSpan().name("inventarioService.findByCodigoSKU | findById");
        try (Tracer.SpanInScope ws = tracer.withSpan(span.start())) {
            if (Objects.nonNull(producto)) {
                InventarioDto inventario = inventarioService.findByCodigoSKU(List.of(
                        InventarioDto.builder()
                                .codigoSKU(producto.getCodigoSKU())
                                .stock(0)
                                .build()
                )).get(0);
                producto.setStock(inventario.getStock());
                new ProductoDto();
                return ProductoDto.setProducto(producto);
            } else {
                throw new ExceptionApp("Producto no encontrado", HttpStatus.NOT_FOUND);
            }
        } finally {
            log.info("Trace id: {}", span.context().traceId());
            span.end();
        }
    }
}
