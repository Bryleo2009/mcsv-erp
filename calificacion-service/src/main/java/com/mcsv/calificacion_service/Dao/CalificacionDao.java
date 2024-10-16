package com.mcsv.calificacion_service.Dao;

import com.mcsv.calificacion_service.Config.Exception.ModeloNotFoundException;
import com.mcsv.calificacion_service.Model.Calificacion;
import com.mcsv.calificacion_service.Repo.ICalificacionRepo;
import com.mcsv.calificacion_service.Service.ICalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CalificacionDao implements ICalificacionService {
    @Autowired
    private ICalificacionRepo repo;


    @Override
    public Calificacion saveCalificacion(Calificacion calificacion) {
        return repo.save(calificacion);
    }

    @Override
    public List<Calificacion> getCalificaciones() {
        return repo.findAll();
    }

    @Override
    public Calificacion getCalificacionById(String id) {
        return repo.findById(id).orElseThrow(() -> new ModeloNotFoundException("Calificacion no encontrada"));
    }

    @Override
    public Calificacion getCalificacionByIdHotel(int idHotel) {
        Calificacion calificacion = repo.findByIdHotel(idHotel);
        if (Objects.isNull(calificacion)) {
            throw new ModeloNotFoundException("Calificacion no encontrada para el hotel con id: " + idHotel);
        }
        return calificacion;
    }

    @Override
    public Calificacion getCalificacionByIdUsuario(int idUsuario) {
        Calificacion calificacion = repo.findByIdUsuario(idUsuario);
        if (Objects.isNull(calificacion)) {
            throw new ModeloNotFoundException("Calificacion no encontrada para el usuario con id: " + idUsuario);
        }
        return calificacion;
    }

    @Override
    public void deleteCalificacion(String id) {
        repo.deleteById(id);
    }

    @Override
    public Calificacion updateCalificacion(Calificacion calificacion) {
        return repo.save(calificacion);
    }
}
