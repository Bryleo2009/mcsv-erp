package com.mcsv.inventario_service.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=8, message = "El codigoSKU debe tener al menos 8 caracteres")
    private String codigoSKU;

    private Integer stock;

    private Integer stockReal;

    @Transient
    private boolean inStock;
}
