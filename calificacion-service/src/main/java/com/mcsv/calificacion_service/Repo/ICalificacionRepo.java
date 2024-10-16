package com.mcsv.calificacion_service.Repo;

import com.mcsv.calificacion_service.Model.Calificacion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ICalificacionRepo extends MongoRepository<Calificacion, String> {
    Calificacion findByIdHotel(int idHotel);
    Calificacion findByIdUsuario(int idUsuario);
}
