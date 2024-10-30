package com.mcsv.producto_service.Repo;

import com.mcsv.producto_service.Model.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IProductoRepo extends MongoRepository<Producto, String> {

}
