package com.mcsv.auth_server.Service;

import com.mcsv.auth_server.Config.Exception.ModeloNotFoundException;
import com.mcsv.auth_server.Dto.AuthUserDto;
import com.mcsv.auth_server.Dto.RequestDto;
import com.mcsv.auth_server.Dto.TokenDto;
import com.mcsv.auth_server.Entity.AuthUser;
import com.mcsv.auth_server.Repository.AuthUserRepository;
import com.mcsv.auth_server.Security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    public AuthUser save(AuthUserDto dto){
        Optional<AuthUser> user = authUserRepository.findByUserName(dto.getUserName());
        if(user.isPresent()){
            return null;
        }
        String password = passwordEncoder.encode(dto.getPassword());
        AuthUser authUser = AuthUser.builder()
                .userName(dto.getUserName())
                .password(password)
                .build();
        return authUserRepository.save(authUser);
    }

    public TokenDto login(AuthUserDto dto){
        Optional<AuthUser> user = authUserRepository.findByUserName(dto.getUserName());
        if(user.isEmpty()){
            throw new ModeloNotFoundException("Usuario no encontrado");
        }
        if(passwordEncoder.matches(dto.getPassword(),user.get().getPassword())){
            return new TokenDto(jwtProvider.createToken(user.get()));
        }
        throw new ModeloNotFoundException("Contraseña incorrecta");
    }

    public TokenDto validate(String token){
        if(!jwtProvider.validate(token)){
            throw new ModeloNotFoundException("Token invalido");
        }
        String userName = jwtProvider.getUserNameFromToken(token);
        if(authUserRepository.findByUserName(userName).isEmpty()){
            throw new ModeloNotFoundException("Usuario no encontrado");
        }
        return new TokenDto(token);
    }
}
