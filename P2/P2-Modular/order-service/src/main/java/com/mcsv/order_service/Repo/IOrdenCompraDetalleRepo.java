package com.mcsv.order_service.Repo;

import com.mcsv.order_service.Model.OrdenCompraDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrdenCompraDetalleRepo extends JpaRepository<OrdenCompraDetalle, Long> {

}
