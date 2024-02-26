package cmartinez.demojwt.repository;

import cmartinez.demojwt.entity.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
    Page<UsuarioEntity> findByIsActiveTrue(Pageable pageable);
    Page<UsuarioEntity> findAll(Pageable pageable);
    Optional<UsuarioEntity> findByEmail(String correo);
}
