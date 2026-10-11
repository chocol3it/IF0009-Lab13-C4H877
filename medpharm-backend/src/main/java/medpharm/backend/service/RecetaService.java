package medpharm.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import medpharm.backend.model.DetalleReceta;
import medpharm.backend.model.Medicamento;
import medpharm.backend.model.RecetaMedica;
import medpharm.backend.model.Usuario;
import medpharm.backend.repository.MedicamentoRepository;
import medpharm.backend.repository.RecetaRepository;
import medpharm.backend.repository.UsuarioRepository;

@Service
public class RecetaService {

    private final MedicamentoRepository medicamentoRepository;
    private final RecetaRepository recetaRepository;
    private final UsuarioRepository usuarioRepository;

    public RecetaService(MedicamentoRepository medicamentoRepository, RecetaRepository recetaRepository,
            UsuarioRepository usuarioRepository) {
        this.medicamentoRepository = medicamentoRepository;
        this.recetaRepository = recetaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<RecetaMedica> obtenerRecetasRegistradas() {
        return recetaRepository.findAll();
    }

    public List<RecetaMedica> filtrarRecetasPorEstado(String estado) {
        return recetaRepository.findByEstado(RecetaMedica.Estado.valueOf(estado.toUpperCase()));
    }

    @Transactional
    public RecetaMedica crearReceta(RecetaMedica receta, String usernameMedico) {

        Usuario medico = usuarioRepository.findByUsername(usernameMedico)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usernameMedico));

        receta.setMedico(medico);
        receta.setEstado(RecetaMedica.Estado.PENDIENTE);
        receta.setFechaEmision(LocalDateTime.now());
        receta.setCodigoReceta("REC-" + LocalDateTime.now().getYear() + "-" + String.format("%03d", recetaRepository.count() + 1));

        for (DetalleReceta detalle : receta.getDetalles()) {

            Medicamento medicamento = medicamentoRepository
                                        .findMedicamentoById(detalle.getMedicamento().getId())
                                        .stream()
                                        .findFirst()
                                        .orElseThrow(() -> new IllegalArgumentException("Medicamento no encontrado con ID"));

            if (medicamento.getStock() < detalle.getCantidad()) {
                throw new IllegalArgumentException("No hay suficiente stock para el medicamento: " + medicamento.getNombre());
            }

            medicamento.setStock(medicamento.getStock() - detalle.getCantidad());
            medicamentoRepository.save(medicamento);

            detalle.setReceta(receta);
            detalle.setMedicamento(medicamento);
        }

        return recetaRepository.save(receta);
    }

    public RecetaMedica actualizarEstadoReceta(Long id, String nuevoEstado) {
        RecetaMedica receta = recetaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Receta no encontrada con ID: " + id));

        receta.setEstado(RecetaMedica.Estado.valueOf(nuevoEstado));
        return recetaRepository.save(receta);

    }

}
