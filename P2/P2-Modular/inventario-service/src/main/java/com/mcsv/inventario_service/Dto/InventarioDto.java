package com.mcsv.inventario_service.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mcsv.inventario_service.Model.Inventario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventarioDto {
    // Campos de la entidad
        private Long id;
    private String codigoSKU;
    private Integer cantidad;
    private boolean inStock;

    @JsonIgnore
    public Inventario getInventario() {
        return Inventario.builder()
                                .id(this.id)
                .codigoSKU(this.codigoSKU)
                .cantidad(this.cantidad)
                .build();
    }

    @JsonIgnore
    public static InventarioDto setInventario(Inventario inventario) {
        return InventarioDto.builder()
                                .id(inventario.getId())
                .codigoSKU(inventario.getCodigoSKU())
                .cantidad(inventario.getCantidad())
                .build();
    }
}
