package com.mcsv.hotel_service.Service;

import com.mcsv.hotel_service.Model.Hotel;

import java.util.List;

public interface IHotelService {
    public List<Hotel> findAll();
    public Hotel findById(Integer id);
    public Hotel findByNombre(String nombre);
    public Hotel save(Hotel hotel);
    public void delete(Integer id);
}
