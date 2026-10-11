package medpharm.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import medpharm.backend.model.Medicamento;
import medpharm.backend.repository.MedicamentoRepository;

@RestController
@RequestMapping("/api/v1/medicamentos")
@CrossOrigin(origins = "http://localhost:4200")
public class MedicamentoController {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoController(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Medicamento>> listarMedicamentos() {
        return ResponseEntity.ok(medicamentoRepository.findAll());
    }
}
