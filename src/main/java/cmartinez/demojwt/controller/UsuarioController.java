package cmartinez.demojwt.controller;

import cmartinez.demojwt.dto.AuthResponse;
import cmartinez.demojwt.dto.LoginDTO;
import cmartinez.demojwt.exception.UserValidationException;
import cmartinez.demojwt.service.AuthService;
import cmartinez.demojwt.service.UsuarioService;
import cmartinez.demojwt.entity.UsuarioEntity;
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
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;



    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDTO request) {
        AuthResponse jwt = usuarioService.autenticarUsuario(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(jwt);
    }


    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioEntity usuario) {
            UsuarioEntity usuarioCreado = usuarioService.crearUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
    }
}