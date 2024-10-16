package com.mcsv.hotel_service.Dao;

import com.mcsv.hotel_service.Config.Exception.ModeloNotFoundException;
import com.mcsv.hotel_service.Model.Hotel;
import com.mcsv.hotel_service.Repo.IHotelRepo;
import com.mcsv.hotel_service.Service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelDao implements IHotelService {
    @Autowired
    private IHotelRepo repo;

    @Override
    public List<Hotel> findAll() {
        return repo.findAll();
    }

    @Override
    public Hotel findById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new ModeloNotFoundException("Hotel no encontrado"));
    }

    @Override
    public Hotel findByNombre(String nombre) {
        return repo.findByNombre(nombre);
    }

    @Override
    public Hotel save(Hotel hotel) {
        return repo.save(hotel);
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
