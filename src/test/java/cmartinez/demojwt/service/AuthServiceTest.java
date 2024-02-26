package cmartinez.demojwt.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private ValidationStrategy emailValidationStrategy;

    @Mock
    private ValidationStrategy passwordValidationStrategy;

    @InjectMocks
    private AuthService authService;

    private UsuarioEntity usuario;



}
