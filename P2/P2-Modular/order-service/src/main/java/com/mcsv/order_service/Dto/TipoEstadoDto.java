package com.mcsv.order_service.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mcsv.order_service.Model.TipoEstado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipoEstadoDto {
    // Campos de la entidad
        private String id;
    private String descripcion;

    @JsonIgnore
    public TipoEstado getTipoEstado() {
        return TipoEstado.builder()
                                .id(this.id)
                .descripcion(this.descripcion)
                .build();
    }

    @JsonIgnore
    public static TipoEstadoDto setTipoEstado(TipoEstado tipoestado) {
        return TipoEstadoDto.builder()
                                .id(tipoestado.getId())
                .descripcion(tipoestado.getDescripcion())
                .build();
    }

    @JsonIgnore
    public TipoEstadoDto(String id) {
        this.id = id;
    }
}
