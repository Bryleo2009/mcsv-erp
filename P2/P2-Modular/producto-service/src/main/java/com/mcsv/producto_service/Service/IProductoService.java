package com.mcsv.producto_service.Service;

import com.mcsv.producto_service.Dto.ProductoDto;
import com.mcsv.producto_service.Model.Producto;

import java.util.List;

public interface IProductoService {
    Producto save(ProductoDto producto);
    void delete(String id);
    Producto update(ProductoDto dto);
    List<ProductoDto> findAll();
    Producto findById(String id);
}
