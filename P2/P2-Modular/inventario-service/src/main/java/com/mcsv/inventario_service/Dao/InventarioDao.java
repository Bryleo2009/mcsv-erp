package com.mcsv.inventario_service.Dao;

import com.mcsv.inventario_service.Config.Exception.ExceptionApp;
import com.mcsv.inventario_service.Dto.InventarioDto;
import com.mcsv.inventario_service.Model.Inventario;
import com.mcsv.inventario_service.Repo.IInventarioRepo;
import com.mcsv.inventario_service.Service.IInventarioService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventarioDao implements IInventarioService {
    @Autowired
    private IInventarioRepo repo;

     @Override
     public List<InventarioDto> findAll() {
         log.info("Buscando todos los inventarios");
         List<Inventario> entities = repo.findAll();
         List<InventarioDto> inventarios = entities.stream()
                 .map(InventarioDto::setInventario)
                 .collect(Collectors.toList());

         inventarios.forEach(inventario -> inventario.setInStock(inStock(inventario.getCodigoSKU())));
         return inventarios;
     }

    @Override
    @SneakyThrows
    public List<InventarioDto> findByCodigoSKU(List<InventarioDto> productos) {
        log.info("Buscando inventario por codigosSKU" + productos);

        List<InventarioDto> inventarios = new ArrayList<>();
        for(InventarioDto inventarioDto : productos) {
            Inventario inventario = repo.findByCodigoSKU(inventarioDto.getCodigoSKU());
            if(Objects.isNull(inventario)) {
                throw new ExceptionApp("Inventario no encontrado");
            }
            boolean presentaStock = inStock(inventarioDto.getCodigoSKU(), inventarioDto.getStock());
            if(presentaStock){
                disminuirStock(inventarioDto.getCodigoSKU(), inventarioDto.getStock());
            }
            inventarioDto.setInStock(presentaStock);
            inventarios.add(inventarioDto);
        }

        return inventarios;
    }

    @Override
    public Inventario findById(Long id) {
        log.info("Buscando inventario por id: " + id);
        return repo.findById(id).orElseThrow(() -> new ExceptionApp("Inventario no encontrado"));
    }

    @Override
    public Inventario save(InventarioDto inventarioDto) {
        log.info("Guardando inventario");

        Inventario inventario = inventarioDto.getInventario();
        inventario.setStockReal(inventario.getStock());
        return repo.save(inventario);
    }

    @Override
    public Inventario update(InventarioDto inventarioDto) {
        log.info("Actualizando inventario");

        Inventario inventario = inventarioDto.getInventario();
        return repo.save(inventario);
    }

    @Override
    public void delete(Long id) {
        log.info("Eliminando inventario por id: " + id);
        repo.deleteById(id);
    }

    @Override
    public boolean inStock(String codigoSKU) {
        log.info("Verificando si hay stock para el inventario con codigoSKU: " + codigoSKU);
        Inventario inventario = repo.findByCodigoSKU(codigoSKU);
        if(Objects.isNull(inventario)) {
            throw new ExceptionApp("Inventario no encontrado");
        }
        return inventario.getStock() > 0;
    }

    public boolean inStock(String codigoSKU, int cantidad) {
        log.info("Verificando si hay stock para el inventario con codigoSKU: " + codigoSKU);
        Inventario inventario = repo.findByCodigoSKU(codigoSKU);
        if(Objects.isNull(inventario)) {
            throw new ExceptionApp("Inventario no encontrado");
        }
        return inventario.getStock() >= cantidad;
    }

    @Override
    public void disminuirStock(String codigoSKU, Integer cantidad) {
        //se disminuye el stock segun la cantidad al realizar una consulta de stock
        Inventario inventario = repo.findByCodigoSKU(codigoSKU);
        if(Objects.isNull(inventario)) {
            throw new ExceptionApp("Inventario no encontrado");
        }
        inventario.setStock(inventario.getStock() - cantidad);
        repo.save(inventario);
    }

    @Override
    public void confirmarDisminuirStock(String codigoSKU, Integer cantidad, boolean confirmar) {
        //se disminuye el stock real segun la cantidad al confirmar la compra, caso contrario se restablece el stock
        Inventario inventario = repo.findByCodigoSKU(codigoSKU);
        if(Objects.isNull(inventario)) {
            throw new ExceptionApp("Inventario no encontrado");
        }
        if(confirmar) {
            inventario.setStockReal(inventario.getStockReal() - cantidad);
        } else {
            inventario.setStock(inventario.getStock() + cantidad);
        }
        repo.save(inventario);
    }
}
