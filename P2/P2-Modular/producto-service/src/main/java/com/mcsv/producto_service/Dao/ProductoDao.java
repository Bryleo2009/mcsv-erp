package com.mcsv.producto_service.Dao;

import com.mcsv.producto_service.Dto.ProductoDto;
import com.mcsv.producto_service.Model.Producto;
import com.mcsv.producto_service.Repo.IProductoRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductoDao {

    @Autowired
    private IProductoRepo repo;

    public Producto save(ProductoDto producto) {
        log.info("Guardando producto {}", producto);
        return repo.save(producto.getProducto());
    }

    public void delete(String id) {
        log.info("Eliminando producto con id {}", id);
        repo.deleteById(id);
    }

    public Producto update(ProductoDto dto) {
        log.info("Actualizando producto {}", dto);
        return repo.save(dto.getProducto());
    }

    public List<ProductoDto> findAll() {
        log.info("Buscando todos los productos");
        List<Producto> productos = repo.findAll();
        return productos.stream().map(ProductoDto::setProducto).collect(Collectors.toList());
    }

    public Producto findById(String id) {
        log.info("Buscando producto con id {}", id);
        return repo.findById(id).orElse(null);
    }
}
