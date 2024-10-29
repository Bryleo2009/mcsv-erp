package com.mcsv.calificacion_service.Service;

import com.mcsv.calificacion_service.Model.Calificacion;

import java.util.List;

public interface ICalificacionService {
    public Calificacion saveCalificacion(Calificacion calificacion);
    public List<Calificacion> getCalificaciones();
    public Calificacion getCalificacionById(String id);
    public List<Calificacion> getCalificacionByIdHotel(int idHotel);
    public List<Calificacion> getCalificacionByIdUsuario(int idUsuario);
    public void deleteCalificacion(String id);
    public Calificacion updateCalificacion(Calificacion calificacion);
}
