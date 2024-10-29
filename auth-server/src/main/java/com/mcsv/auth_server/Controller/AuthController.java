package com.mcsv.auth_server.Controller;

import com.mcsv.auth_server.Config.Exception.ModeloNotFoundException;
import com.mcsv.auth_server.Dto.AuthUserDto;
import com.mcsv.auth_server.Dto.RequestDto;
import com.mcsv.auth_server.Dto.TokenDto;
import com.mcsv.auth_server.Entity.AuthUser;
import com.mcsv.auth_server.Service.AuthService;
import com.mcsv.usuario_service.Dao.UsuarioDao;
import com.mcsv.usuario_service.Model.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody AuthUserDto authUserDto){
        TokenDto tokenDto = authService.login(authUserDto);
        if (Objects.isNull(tokenDto)) {
             throw new ModeloNotFoundException("Usuario no encontrado");
        }
        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestParam(required = false) String token, HttpServletRequest request) {
        // Si el token no se envía como parámetro, verificar en el header "Authorization"
        if (token == null || token.isEmpty()) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7); // Extraer el token después de "Bearer "
            }
        }

        // Validar el token
        TokenDto tokenDto = authService.validate(token);
        if (tokenDto == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(tokenDto);
    }


    @PostMapping("/create")
    public ResponseEntity<Usuario> create(@Valid @RequestBody AuthUserDto dto){
        //builder de usuario
        Usuario usuario = new Usuario().builder()
                .email(dto.getUserName())
                .password(dto.getPassword())
                .build();

        //buscar usuario en la base de datos
        Usuario authUser = authService.save(usuario);
        if(authUser == null){
            throw new ModeloNotFoundException("Usuario no creado");
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
