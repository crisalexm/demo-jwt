package cmartinez.demojwt.controller;

import cmartinez.demojwt.dto.AuthResponse;
import cmartinez.demojwt.dto.LoginDTO;
import cmartinez.demojwt.service.AuthService;
import cmartinez.demojwt.entity.UsuarioEntity;
import cmartinez.demojwt.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDTO request) {
        AuthResponse jwt = authService.autenticarUsuario(request.getEmail(), request.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioEntity usuario) {
            UsuarioEntity usuarioCreado = userService.createUser(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
    }
}