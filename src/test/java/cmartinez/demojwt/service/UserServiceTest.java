package cmartinez.demojwt.service;

import cmartinez.demojwt.entity.TelefonoEntity;
import cmartinez.demojwt.entity.UsuarioEntity;
import cmartinez.demojwt.exception.UserValidationException;
import cmartinez.demojwt.repository.UsuarioRepository;
import cmartinez.demojwt.strategies.validations.ValidationStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ValidationService validationService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    private UsuarioEntity usuario;

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
        assertThrows(UserValidationException.class, () -> userService.createUser(nuevoUsuario), "El email ya está en uso.");
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

        UsuarioEntity createdUser = userService.createUser(newUser);

        assertNotNull(createdUser);
        assertEquals(newUser.getEmail(), createdUser.getEmail());
        verify(usuarioRepository).save(any(UsuarioEntity.class));
    }



    @Test
    void getUsersSuccessfully() {
        // Configura la respuesta esperada del repositorio
        Pageable pageable = PageRequest.of(0, 10);
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setName("Cristhian");
        usuario.setId(UUID.randomUUID());
        usuario.setEmail("cristhian@email.com");
        usuario.setPassword("Password123!");
        usuario.setPhones(Arrays.asList(new TelefonoEntity("12345678", "1", "57")));

        Page<UsuarioEntity> pagedResponse = new PageImpl<>(Arrays.asList(usuario));
        when(usuarioRepository.findAll(pageable)).thenReturn(pagedResponse);

        // Ejecuta el método bajo prueba
        Map<String, Object> users = userService.getUsers(true, 0, 10);

        // Verifica los resultados
        assertNotNull(users);
        assertTrue(users.containsKey("users"));
        assertTrue(users.containsKey("currentPage"));
        assertTrue(users.containsKey("totalItems"));
        assertTrue(users.containsKey("totalPages"));
        assertEquals(1, ((List) users.get("users")).size()); // Asegura que la lista contenga el usuario
    }

    @Test
    void getUserByIdSuccessfully() {
        UUID userId = UUID.randomUUID();
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(userId);
        // Configura los demás campos del usuario según sea necesario

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Ejecuta el método bajo prueba
        UsuarioEntity foundUser = userService.getUserById(userId);

        // Verifica los resultados
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
    }

    @Test
    void getUserByIdWithUserNotFoundException() {
        UUID userId = UUID.randomUUID();
        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        // Ejecuta el método bajo prueba y verifica que se lanza la excepción
        Exception exception = assertThrows(UserValidationException.class, () -> userService.getUserById(userId));
        assertEquals("Usuario no encontrado.", exception.getMessage());
    }



    @Test
    void deactivateUserSuccessfully() {
        UUID userId = UUID.randomUUID();
        UsuarioEntity existingUser = new UsuarioEntity();
        existingUser.setId(userId);
        existingUser.setActive(true);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        doAnswer(invocation -> {
            UsuarioEntity user = invocation.getArgument(0);
            user.setActive(false);
            return null;
        }).when(usuarioRepository).save(any(UsuarioEntity.class));

        userService.deactivateUser(userId);

        assertFalse(existingUser.isActive());
        verify(usuarioRepository).save(existingUser);
    }
}
