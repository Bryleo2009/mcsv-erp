package com.mcsv.usuario_service.Service.External;

import com.mcsv.hotel_service.Model.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "hotel-service") //nombre del servicio al que se va a conectar
public interface IHotelService {

    @GetMapping("/hotel/{id}")
    Hotel getHotelById(Integer id);
}
