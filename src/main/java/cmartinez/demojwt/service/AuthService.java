package cmartinez.demojwt.service;

import cmartinez.demojwt.dto.AuthResponse;
import cmartinez.demojwt.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    public AuthResponse login(LoginDTO loginRequest) {
        return null;
    }
}
