package com.mcsv.auth_server.Service;

import com.mcsv.auth_server.Config.Exception.ModeloNotFoundException;
import com.mcsv.auth_server.Dto.AuthUserDto;
import com.mcsv.auth_server.Dto.TokenDto;
import com.mcsv.auth_server.Entity.AuthUser;
import com.mcsv.auth_server.Repository.AuthUserRepository;
import com.mcsv.auth_server.Security.JwtProvider;
import com.mcsv.auth_server.Service.External.IUsuarioService;
import com.mcsv.usuario_service.Model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private IUsuarioService usuarioService;

    public Usuario save(Usuario dto){
        Usuario user = usuarioService.getUsuarioByEmail(dto.getEmail());
        if(!Objects.isNull(user)){
            throw new ModeloNotFoundException("Usuario ya existe");
        }
        String password = passwordEncoder.encode(dto.getPassword());
        Usuario usuario = new Usuario().builder()
                .email(dto.getEmail())
                .password(password)
                .build();
        return usuarioService.saveUsuario(usuario);
    }

    public TokenDto login(AuthUserDto dto){
        Usuario user = usuarioService.getUsuarioByEmail(dto.getUserName());
        if(Objects.isNull(user)){
            throw new ModeloNotFoundException("Usuario no encontrado");
        }
        if(passwordEncoder.matches(dto.getPassword(),user.getPassword())){
            return new TokenDto(jwtProvider.createToken(user));
        }
        throw new ModeloNotFoundException("Contraseña incorrecta");
    }

    public TokenDto validate(String token){
        if(!jwtProvider.validate(token)){
            throw new ModeloNotFoundException("Token invalido");
        }
        String userName = jwtProvider.getUserNameFromToken(token);
        if(Objects.isNull(usuarioService.getUsuarioByEmail(userName))){
            throw new ModeloNotFoundException("Usuario no encontrado");
        }
        return new TokenDto(token);
    }
}
