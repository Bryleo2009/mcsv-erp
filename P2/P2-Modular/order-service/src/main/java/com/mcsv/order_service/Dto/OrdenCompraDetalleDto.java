package com.mcsv.order_service.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mcsv.order_service.Model.OrdenCompraDetalle;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdenCompraDetalleDto {
    // Campos de la entidad
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigoSKU;
    private BigDecimal precio;
    private Integer cantidad;
    private BigDecimal total;
    private BigDecimal descuento;

    @JsonIgnore
    public OrdenCompraDetalle getOrdenCompraDetalle() {
        return OrdenCompraDetalle.builder()
                                .id(this.id)
                .codigoSKU(this.codigoSKU)
                .precio(this.precio)
                .cantidad(this.cantidad)
                .total(this.total)
                .descuento(this.descuento)
                .build();
    }

    @JsonIgnore
    public static OrdenCompraDetalleDto setOrdenCompraDetalle(OrdenCompraDetalle ordencompradetalle) {
        return OrdenCompraDetalleDto.builder()
                                .id(ordencompradetalle.getId())
                .codigoSKU(ordencompradetalle.getCodigoSKU())
                .precio(ordencompradetalle.getPrecio())
                .cantidad(ordencompradetalle.getCantidad())
                .total(ordencompradetalle.getTotal())
                .descuento(ordencompradetalle.getDescuento())
                .build();
    }
}
