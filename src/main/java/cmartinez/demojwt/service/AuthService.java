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
    private JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ValidationService validationService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            ValidationService validationService,
            BCryptPasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.validationService = validationService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UsuarioEntity createUser(UsuarioEntity user) {
        validateEmailAndPassword(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Date now = new Date();
        user.setCreated(now);
        user.setModified(now);
        user.setLastLogin(now);
        user.setActive(true);

        String token = jwtService.generateToken(user.getEmail());
        user.setToken(token);

        return usuarioRepository.save(user);
    }

    private void validateEmailAndPassword(UsuarioEntity user) {
        if (!validationService.isValidEmail(user.getEmail()) || !validationService.isValidPassword(user.getPassword())) {
            throw new UserValidationException("Correo electrónico o contraseña inválidos.");
        }

        Optional<UsuarioEntity> existingUser = usuarioRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new UserValidationException("El email ya está en uso.");
        }
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