package com.mcsv.order_service.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mcsv.producto_service.Model.Producto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orden_compra_detalle")
public class OrdenCompraDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=8, message = "El codigoSKU debe tener al menos 8 caracteres")
    @Column(name = "codigoSKU")
    private String codigoSKU;

    @Column(name = "precio", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal precio;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "total", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal total;

    @Column(name = "descuento", columnDefinition = "DECIMAL(5,2)", nullable = true)
    private BigDecimal descuento;

    @ManyToOne
    @JoinColumn(name = "orden_compra_numero_orden", nullable = false)
    @JsonIgnore
    private OrdenCompra ordenCompra;
}
