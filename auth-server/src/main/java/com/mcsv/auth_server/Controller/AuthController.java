package com.mcsv.auth_server.Controller;

import com.mcsv.auth_server.Dto.AuthUserDto;
import com.mcsv.auth_server.Dto.RequestDto;
import com.mcsv.auth_server.Dto.TokenDto;
import com.mcsv.auth_server.Entity.AuthUser;
import com.mcsv.auth_server.Service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody AuthUserDto authUserDto){
        if(authUserDto == null){
            return ResponseEntity.badRequest().build();
        }
        TokenDto tokenDto = authService.login(authUserDto);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestParam(required = false) String token, HttpServletRequest request) {
        System.out.println("Token: " + token);

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
    public ResponseEntity<AuthUser> create(@RequestBody AuthUserDto dto){
        AuthUser authUser = authService.save((dto));
        if(authUser == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(authUser);
    }
}
