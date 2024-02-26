package cmartinez.demojwt.service;

import cmartinez.demojwt.dto.AuthResponse;
import cmartinez.demojwt.entity.UsuarioEntity;
import cmartinez.demojwt.exception.UserValidationException;
import cmartinez.demojwt.repository.UsuarioRepository;
import cmartinez.demojwt.strategies.validations.ValidationStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;


    public AuthService(
            JwtService jwtService,
            UsuarioRepository usuarioRepository) {
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }



    public AuthResponse autenticarUsuario(String email, String password) {

        UsuarioEntity usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UserValidationException("Usuario no encontrado o contraseña incorrecta"));

        if (!new BCryptPasswordEncoder().matches(password, usuario.getPassword())) {
            throw new UserValidationException("Usuario no encontrado o contraseña incorrecta");
        }
        String token = jwtService.generateToken(usuario.getEmail());
        return AuthResponse.builder()
                .jwt(token)
                .build();
    }

}