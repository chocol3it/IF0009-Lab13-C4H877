package medpharm.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import medpharm.backend.model.RecetaMedica;
import medpharm.backend.service.RecetaService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping ("/api/v1/recetas")
@CrossOrigin(origins = "http://localhost:4200")
public class RecetaController {

    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @GetMapping
    public ResponseEntity<List<RecetaMedica>> listarRecetas() {
        return ResponseEntity.ok(recetaService.obtenerRecetasRegistradas());
    }

    @PostMapping
    public ResponseEntity<RecetaMedica> crearNuevaReceta(@RequestBody RecetaMedica receta, Authentication authentication) {
        RecetaMedica nuevaReceta = recetaService.crearReceta(receta, authentication.getName());
        return ResponseEntity.ok(nuevaReceta);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<RecetaMedica>> filtrarRecetasPorEstado(@PathVariable String estado) {
        List<RecetaMedica> recetasFiltradas = recetaService.filtrarRecetasPorEstado(estado);
        return ResponseEntity.ok(recetasFiltradas);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<RecetaMedica> actualizarEstadoReceta(@PathVariable Long id, @RequestParam String nuevoEstado) {
        RecetaMedica recetaActualizada = recetaService.actualizarEstadoReceta(id, nuevoEstado);
        return ResponseEntity.ok(recetaActualizada);
    }

}
