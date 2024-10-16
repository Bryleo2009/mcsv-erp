package com.mcsv.hotel_service.Repo;

import com.mcsv.hotel_service.Model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHotelRepo extends JpaRepository<Hotel, Integer> {
    Hotel findByNombre(String nombre);
}
