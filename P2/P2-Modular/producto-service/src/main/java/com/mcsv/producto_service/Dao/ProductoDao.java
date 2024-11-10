package com.mcsv.producto_service.Dao;

import com.mcsv.producto_service.Dto.ProductoDto;
import com.mcsv.producto_service.Model.Producto;
import com.mcsv.producto_service.Repo.IProductoRepo;
import com.mcsv.producto_service.Service.IProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductoDao implements IProductoService {

    @Autowired
    private IProductoRepo repo;

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
        return productos.stream().map(ProductoDto::setProducto).collect(Collectors.toList());
    }

    @Override
    public Producto findById(String id) {
        log.info("Buscando producto con id {}", id);
        return repo.findById(id).orElse(null);
    }
}
