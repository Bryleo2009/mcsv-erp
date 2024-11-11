package com.mcsv.producto_service.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mcsv.producto_service.Model.Producto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoDto {
    private String id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String codigoSKU;
    private int stock;

    @JsonIgnore
    public Producto getProducto() {
        return Producto.builder()
                .id(this.id)
                .nombre(this.nombre)
                .descripcion(this.descripcion)
                .precio(this.precio)
                .codigoSKU(this.codigoSKU)
                .stock(this.stock)
                .build();
    }

    @JsonIgnore
    public static ProductoDto setProducto(Producto producto) {
        return ProductoDto.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .codigoSKU(producto.getCodigoSKU())
                .stock(producto.getStock())
                .build();
    }

}
