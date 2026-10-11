package medpharm.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import medpharm.backend.model.RecetaMedica;

@Repository
public interface RecetaRepository extends JpaRepository<RecetaMedica, Long> {

    List<RecetaMedica> findByEstado(RecetaMedica.Estado estado);

}
