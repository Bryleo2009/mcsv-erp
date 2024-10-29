package com.mcsv.calificacion_service.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "calificacion")
public class Calificacion {
    @Id
    private String id;
    private int idHotel;
    private int idUsuario;
    private int calificacion;
    private String comentario;
}
