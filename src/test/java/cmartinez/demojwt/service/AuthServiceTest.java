package cmartinez.demojwt.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import cmartinez.demojwt.entity.TelefonoEntity;
import cmartinez.demojwt.entity.UsuarioEntity;
import cmartinez.demojwt.exception.UserValidationException;
import cmartinez.demojwt.repository.UsuarioRepository;
import cmartinez.demojwt.strategies.validations.ValidationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private ValidationService validationService;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void createUserWithExistingEmail() {
        // Configura un usuario existente
        String existingEmail = "usuarioexistente@test.com";
        UsuarioEntity usuarioExistente = new UsuarioEntity();
        usuarioExistente.setEmail(existingEmail);
        usuarioExistente.setPassword("Password123!");
        usuarioExistente.setPhones(Arrays.asList(new TelefonoEntity("12345678", "1", "57")));

        when(usuarioRepository.findByEmail(existingEmail)).thenReturn(Optional.of(usuarioExistente));

        // Crea un nuevo usuario con el mismo correo electrónico
        UsuarioEntity nuevoUsuario = new UsuarioEntity();
        nuevoUsuario.setEmail(existingEmail);
        nuevoUsuario.setPassword("Password123!");
        nuevoUsuario.setPhones(Arrays.asList(new TelefonoEntity("12345678", "1", "57")));

        // Configura los mocks para validar el correo y la contraseña correctamente
        when(validationService.isValidEmail(any())).thenReturn(true);
        when(validationService.isValidPassword(any())).thenReturn(true);

        // Verifica que se lance una excepción
        assertThrows(UserValidationException.class, () -> authService.createUser(nuevoUsuario), "El email ya está en uso.");
    }

    @Test
    void createUserSuccessfully() {
        UsuarioEntity newUser = new UsuarioEntity();
        newUser.setEmail("nuevoUsuario@test.com");
        newUser.setPassword("Password123!");
        newUser.setPhones(Arrays.asList(new TelefonoEntity("12345678", "1", "57")));

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(validationService.isValidEmail(anyString())).thenReturn(true);
        when(validationService.isValidPassword(anyString())).thenReturn(true);
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(newUser);

        UsuarioEntity createdUser = authService.createUser(newUser);

        assertNotNull(createdUser);
        assertEquals(newUser.getEmail(), createdUser.getEmail());
        verify(usuarioRepository).save(any(UsuarioEntity.class));
    }

}
