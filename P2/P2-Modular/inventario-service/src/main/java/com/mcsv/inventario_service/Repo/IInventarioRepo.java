package com.mcsv.inventario_service.Repo;

import com.mcsv.inventario_service.Model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInventarioRepo extends JpaRepository<Inventario, Long> {
    Inventario findByCodigoSKU(String codigoSKU);
}
