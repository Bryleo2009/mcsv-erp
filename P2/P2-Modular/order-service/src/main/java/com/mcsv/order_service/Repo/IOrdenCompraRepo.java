package com.mcsv.order_service.Repo;

import com.mcsv.order_service.Model.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrdenCompraRepo extends JpaRepository<OrdenCompra, String> {

}
