package com.mcsv.order_service.Repo;

import com.mcsv.order_service.Model.TipoEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoEstadoRepo extends JpaRepository<TipoEstado, String> {

}
