package com.mcsv.order_service.Service;

import com.mcsv.order_service.Dto.OrdenCompraDto;
import com.mcsv.order_service.Model.OrdenCompra;

import java.util.List;

public interface IOrdenCompraService {
    List<OrdenCompraDto> findAll();
    OrdenCompra findById(String id);
    OrdenCompra save(OrdenCompraDto ordencompra);
    OrdenCompra update(OrdenCompraDto ordencompra);
    void delete(String id);
}
