package cmartinez.demojwt.service;

import cmartinez.demojwt.dto.AuthResponse;
import cmartinez.demojwt.entity.UsuarioEntity;
import cmartinez.demojwt.exception.UserValidationException;
import cmartinez.demojwt.repository.UsuarioRepository;
import cmartinez.demojwt.service.JWT.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private JwtService jwtService;

    @Value("${usuario.password.regex}")
    private String passwordRegex;

    @Value("${usuario.email.regex}")
    private String emailRegex;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioEntity crearUsuario(UsuarioEntity usuario) {
        if (!isValidEmail(usuario.getEmail())) {
            throw new UserValidationException("Correo electrónico inválido.");
        }
        if (!isValidPassword(usuario.getPassword())) {
            throw new UserValidationException("Contraseña inválida.");
        }

        Optional<UsuarioEntity> existingUser = usuarioRepository.findByEmail(usuario.getEmail());
        if (existingUser.isPresent()) {
            throw new UserValidationException("El correo electrónico ya está en uso.");
        }

        usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        // Establecer las fechas
        Date now = new Date();
        usuario.setCreated(now);
        usuario.setModified(now);
        usuario.setLastLogin(now);

        // Establecer isActive a true
        usuario.setIsActive(true);


        String token = jwtService.generateToken(usuario.getEmail());
        usuario.setToken(token);

        return usuarioRepository.save(usuario);
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
    public Map<String, Object> getAllUsers() {
        List<UsuarioEntity> users = usuarioRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("count", users.size());
        response.put("users", users);
        return response;
    }

    public boolean isValidPassword(String password) {

        Pattern pattern = Pattern.compile(passwordRegex);
        return pattern.matcher(password).matches();
    }


    public boolean isValidEmail(String email) {

        if (email == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}