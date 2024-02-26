package cmartinez.demojwt.service;

import cmartinez.demojwt.entity.TelefonoEntity;
import cmartinez.demojwt.entity.UsuarioEntity;
import cmartinez.demojwt.exception.UserValidationException;
import cmartinez.demojwt.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DemoServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private DemoService demoService;

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
        Map<String, Object> users = demoService.getUsers(true, 0, 10);

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
        UsuarioEntity foundUser = demoService.getUserById(userId);

        // Verifica los resultados
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
    }

    @Test
    void getUserByIdWithUserNotFoundException() {
        UUID userId = UUID.randomUUID();
        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        // Ejecuta el método bajo prueba y verifica que se lanza la excepción
        Exception exception = assertThrows(UserValidationException.class, () -> demoService.getUserById(userId));
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

        demoService.deactivateUser(userId);

        assertFalse(existingUser.isActive());
        verify(usuarioRepository).save(existingUser);
    }
}
