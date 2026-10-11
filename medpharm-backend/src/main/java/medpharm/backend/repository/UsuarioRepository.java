package medpharm.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import medpharm.backend.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);
}
