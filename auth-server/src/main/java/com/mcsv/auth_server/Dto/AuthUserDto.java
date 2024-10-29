package com.mcsv.auth_server.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthUserDto {

    @Email(message = "El userName debe tener un formato válido de email")
    private String userName;

    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

}
