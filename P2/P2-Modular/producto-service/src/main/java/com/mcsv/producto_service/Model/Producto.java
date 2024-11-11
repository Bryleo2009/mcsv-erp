package com.mcsv.producto_service.Model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto {
    @Id
    private String id;

    @Size(min=8, message = "El codigoSKU debe tener al menos 8 caracteres")
    private String codigoSKU;

    @Size(min=3, message = "El nombre debe tener al menos 3 caracteres")
    private String nombre;

    @Size(min=10, message = "La descripcion debe tener al menos 10 caracteres")
    private String descripcion;

    private BigDecimal precio;

    @Transient
    private int stock;
}
