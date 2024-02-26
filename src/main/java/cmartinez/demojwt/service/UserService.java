package cmartinez.demojwt.service;

import cmartinez.demojwt.entity.UsuarioEntity;
import cmartinez.demojwt.exception.UserValidationException;
import cmartinez.demojwt.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UsuarioRepository usuarioRepository;
    private final ValidationService validationService;
    private final BCryptPasswordEncoder passwordEncoder;
    private JwtService jwtService;

    public UserService(UsuarioRepository usuarioRepository,
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

    public void deactivateUser(UUID userId) {
        UsuarioEntity user = usuarioRepository.findById(userId)
                .orElseThrow(() -> new UserValidationException("Usuario no encontrado."));
        user.setActive(false);
        user.setModified(new Date());
        usuarioRepository.save(user);
    }

    public Map<String, Object> getUsers(boolean includeInactive, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<UsuarioEntity> pagedResult;

        if (includeInactive) {
            pagedResult = usuarioRepository.findAll(paging);
        } else {
            pagedResult = usuarioRepository.findByIsActiveTrue(paging);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("users", pagedResult.getContent());
        response.put("currentPage", pagedResult.getNumber());
        response.put("totalItems", pagedResult.getTotalElements());
        response.put("totalPages", pagedResult.getTotalPages());

        return response;
    }

    public UsuarioEntity getUserById(UUID userId) {
        return usuarioRepository.findById(userId).orElseThrow(() -> new UserValidationException("Usuario no encontrado."));
    }
}
