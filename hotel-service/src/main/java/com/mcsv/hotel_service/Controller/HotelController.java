package com.mcsv.hotel_service.Controller;

import com.mcsv.hotel_service.Dao.HotelDao;
import com.mcsv.hotel_service.Model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelDao hotelDao;

    @PostMapping
    public ResponseEntity<?> saveHotel(@RequestBody Hotel request) {
        Hotel hotel = hotelDao.save(request);
        return new ResponseEntity<>(hotel, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateHotel(@RequestBody Hotel request) {
        Hotel hotel = hotelDao.save(request);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteHotel(@RequestBody Hotel request) {
        hotelDao.delete(request.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHotel(@PathVariable int id) {
        Hotel hotel = hotelDao.findById(id);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> getHotelByNombre(@PathVariable String nombre) {
        Hotel hotel = hotelDao.findByNombre(nombre);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllHoteles() {
        return new ResponseEntity<>(hotelDao.findAll(), HttpStatus.OK);
    }
}
