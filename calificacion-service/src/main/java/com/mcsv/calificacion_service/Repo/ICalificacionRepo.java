package com.mcsv.calificacion_service.Repo;

import com.mcsv.calificacion_service.Model.Calificacion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ICalificacionRepo extends MongoRepository<Calificacion, String> {
    List<Calificacion> findByIdHotel(int idHotel);
    List<Calificacion> findByIdUsuario(int idUsuario);
}
