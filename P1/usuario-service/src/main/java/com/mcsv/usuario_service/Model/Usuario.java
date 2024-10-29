package com.mcsv.usuario_service.Model;

import com.mcsv.calificacion_service.Model.Calificacion;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Column(nullable = true)
    private String nombre;

    @Size(max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    @Column(nullable = true)
    private String apellido;

    @Email(message = "El email debe tener un formato válido")
    @Size(max = 100, message = "El email no debe exceder los 100 caracteres")
    private String email;

    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @Transient
    private List<Calificacion> calificaciones;
}
