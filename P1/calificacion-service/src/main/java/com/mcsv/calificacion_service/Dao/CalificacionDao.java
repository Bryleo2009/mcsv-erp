package com.mcsv.calificacion_service.Dao;

import com.mcsv.calificacion_service.Config.Exception.ModeloNotFoundException;
import com.mcsv.calificacion_service.Model.Calificacion;
import com.mcsv.calificacion_service.Repo.ICalificacionRepo;
import com.mcsv.calificacion_service.Service.ICalificacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public List<Calificacion> getCalificacionByIdHotel(int idHotel) {
        return Optional.ofNullable(repo.findByIdHotel(idHotel)).orElseGet(ArrayList::new);
    }

    @Override
    public List<Calificacion> getCalificacionByIdUsuario(int idUsuario) {
        return Optional.ofNullable(repo.findByIdUsuario(idUsuario)).orElseGet(ArrayList::new);
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
