package com.mcsv.order_service.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mcsv.order_service.Model.OrdenCompra;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdenCompraDto {
    // Campos de la entidad
    @Id
    private String numeroOrden;
    private List<OrdenCompraDetalleDto> detalle;
    private LocalDateTime fecha;
    private TipoEstadoDto tipoEstado;

    @JsonIgnore
    public OrdenCompra getOrdenCompra() {
        return OrdenCompra.builder()
                .numeroOrden(this.numeroOrden)
                .detalle(this.detalle.stream().map(OrdenCompraDetalleDto::getOrdenCompraDetalle).toList())
                .fecha(this.fecha)
                .tipoEstado(this.tipoEstado.getTipoEstado())
                .build();
    }

    @JsonIgnore
    public static OrdenCompraDto setOrdenCompra(OrdenCompra ordencompra) {
        return OrdenCompraDto.builder()
                .numeroOrden(ordencompra.getNumeroOrden())
                .detalle(ordencompra.getDetalle().stream().map(OrdenCompraDetalleDto::setOrdenCompraDetalle).toList())
                .fecha(ordencompra.getFecha())
                .tipoEstado(TipoEstadoDto.setTipoEstado(ordencompra.getTipoEstado()))
                .build();
    }

    @JsonIgnore
    public void iniciales(){
        this.numeroOrden = UUID.randomUUID().toString();
        this.fecha = LocalDateTime.now();
        this.tipoEstado = new TipoEstadoDto("PD");
    }
}
