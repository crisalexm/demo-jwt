package cmartinez.demojwt.service;

import cmartinez.demojwt.entity.UsuarioEntity;
import cmartinez.demojwt.exception.UserValidationException;
import cmartinez.demojwt.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DemoService {
    private final UsuarioRepository usuarioRepository;

    private JwtService jwtService;

    public DemoService(UsuarioRepository usuarioRepository,
                       ValidationService validationService,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
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
