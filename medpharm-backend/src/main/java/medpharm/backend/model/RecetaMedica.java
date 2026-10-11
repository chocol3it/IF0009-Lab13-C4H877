package medpharm.backend.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table (name = "receta_medica")
public class RecetaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column (name = "codigo_receta", unique=true)
    private String codigoReceta;

    @Column (name = "paciente_nombre", nullable = false)
    private String pacienteNombre;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private Usuario medico;

    public enum Estado {
        PENDIENTE,
        DESPACHADA,
        CANCELADA
    }

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private Estado estado;

    @Column (nullable = false)
    private LocalDateTime fechaEmision;

    @OneToMany(mappedBy = "receta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DetalleReceta> detalles = new ArrayList<>();

    public RecetaMedica() {
    }

    public RecetaMedica(Long id, String codigoReceta, String pacienteNombre, Usuario medico,
            Estado estado, LocalDateTime fechaEmision, List<DetalleReceta> detalles) {
        this.id = id;
        this.codigoReceta = codigoReceta;
        this.pacienteNombre = pacienteNombre;
        this.medico = medico;
        this.estado = estado;
        this.fechaEmision = fechaEmision;
        this.detalles = detalles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoReceta() {
        return codigoReceta;
    }

    public void setCodigoReceta(String codigoReceta) {
        this.codigoReceta = codigoReceta;
    }

    public String getPacienteNombre() {
        return pacienteNombre;
    }

    public void setPacienteNombre(String pacienteNombre) {
        this.pacienteNombre = pacienteNombre;
    }

    public Usuario getMedico() {
        return medico;
    }

    public void setMedico(Usuario medico) {
        this.medico = medico;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public List<DetalleReceta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleReceta> detalles) {
        this.detalles = detalles;
    }

}
