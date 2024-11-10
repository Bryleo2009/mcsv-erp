package com.mcsv.inventario_service.Service;

import com.mcsv.inventario_service.Dto.InventarioDto;
import com.mcsv.inventario_service.Model.Inventario;

import java.util.List;

public interface IInventarioService {
    List<InventarioDto> findAll();
    List<InventarioDto> findByCodigoSKU(List<InventarioDto>  codigosSKU);
    Inventario findById(Long id);
    Inventario save(InventarioDto inventario);
    Inventario update(InventarioDto inventario);
    void delete(Long id);
    boolean inStock(String codigoSKU);
    void disminuirStock(String codigoSKU, Integer cantidad);
    void confirmarDisminuirStock(String codigoSKU, Integer cantidad, boolean confirmar);
}
