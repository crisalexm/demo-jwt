package cmartinez.demojwt.service;

import cmartinez.demojwt.entity.UsuarioEntity;
import cmartinez.demojwt.exception.UserValidationException;
import cmartinez.demojwt.repository.UsuarioRepository;
import cmartinez.demojwt.service.JWT.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UsuarioService {
    @Autowired
    private JwtUtil jwtUtil;

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

        // Establecer las fechas
        Date now = new Date();
        usuario.setCreated(now);
        usuario.setModified(now);
        usuario.setLastLogin(now);

        // Establecer isActive a true
        usuario.setIsActive(true);


        String token = jwtUtil.generateToken(usuario.getEmail());
        usuario.setToken(token);

        return usuarioRepository.save(usuario);
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