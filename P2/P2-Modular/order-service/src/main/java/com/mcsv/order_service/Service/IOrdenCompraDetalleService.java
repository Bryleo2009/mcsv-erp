package com.mcsv.order_service.Service;

import com.mcsv.order_service.Dto.OrdenCompraDetalleDto;
import com.mcsv.order_service.Model.OrdenCompra;
import com.mcsv.order_service.Model.OrdenCompraDetalle;

import java.util.List;

public interface IOrdenCompraDetalleService {
    List<OrdenCompraDetalleDto> findAll();
    List<OrdenCompraDetalle> saveAll(List<OrdenCompraDetalle> ordencompradetalle);
    OrdenCompraDetalle findById(Long id);
    OrdenCompraDetalle save(OrdenCompraDetalle ordencompradetalle);
    OrdenCompraDetalle update(OrdenCompraDetalle ordencompradetalle);
    void delete(Long id);
}
