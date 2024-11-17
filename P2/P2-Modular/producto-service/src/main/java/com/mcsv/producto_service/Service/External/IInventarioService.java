package com.mcsv.producto_service.Service.External;

import com.mcsv.inventario_service.Dto.InventarioDto;
import com.mcsv.producto_service.Config.Trazabilidad.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "INVENTARIO-SERVICE", configuration = FeignConfig.class)
public interface IInventarioService {

    String url = "/inventario";

    @PostMapping(url)
    void saveInventario(@RequestBody InventarioDto request);

    /**
     * Busca un inventario por su codigoSKU, y disminuye el stock en caso de que haya suficiente
     * @param codigosSKU lista de productos a buscar
     */
    @PostMapping(url+"/findByCodigoSKU")
    List<InventarioDto> findByCodigoSKU(@RequestBody List<InventarioDto> codigosSKU);

}


