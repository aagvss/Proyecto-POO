package cl.proyecto.poo.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class SolicitudAdopcion {
    private final String id;
    private final String adoptanteId;
    private final String mascotaId;
    private EstadoSolicitud estado;
    private LocalDateTime fechaSolicitud;
    private String motivoRechazo; // si aplica

    public SolicitudAdopcion(String adoptanteId, String mascotaId) {
        this.id = "SOL-" + UUID.randomUUID().toString().substring(0,8);
        this.adoptanteId = adoptanteId;
        this.mascotaId = mascotaId;
        this.estado = EstadoSolicitud.BORRADOR;
        this.fechaSolicitud = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getAdoptanteId() { return adoptanteId; }
    public String getMascotaId() { return mascotaId; }
    public EstadoSolicitud getEstado() { return estado; }
    public void setEstado(EstadoSolicitud estado) { this.estado = estado; }
    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public String getMotivoRechazo() { return motivoRechazo; }
    public void setMotivoRechazo(String motivoRechazo) { this.motivoRechazo = motivoRechazo; }

    @Override
    public String toString() {
        return "SolicitudAdopcion{" +
                "id='" + id + '\'' +
                ", adoptanteId='" + adoptanteId + '\'' +
                ", mascotaId='" + mascotaId + '\'' +
                ", estado=" + estado +
                ", fechaSolicitud=" + fechaSolicitud +
                ", motivoRechazo='" + motivoRechazo + '\'' +
                '}';
    }

}
