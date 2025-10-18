package cl.proyecto.poo.model;

import java.time.LocalDateTime;
import java.util.Objects;


public class Usuario {
    private final String id;
    private final String email;           // Usado como username
    private String password;              // Encriptada
    private final Rol rol;
    private String adoptanteId;           // Solo si el rol es ADOPTANTE
    private EstadoUsuario estado;
    private final LocalDateTime fechaCreacion;
    private LocalDateTime ultimoLogin;
    private int intentosLoginFallidos;

    public Usuario(String id, String email, String password, Rol rol, String adoptanteId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.adoptanteId = adoptanteId;
        this.estado = EstadoUsuario.ACTIVO;
        this.fechaCreacion = LocalDateTime.now().withNano(0);
        this.ultimoLogin = null;
        this.intentosLoginFallidos = 0;
    }

    // Getters
    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Rol getRol() { return rol; }
    public String getAdoptanteId() { return adoptanteId; }
    public EstadoUsuario getEstado() { return estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getUltimoLogin() { return ultimoLogin; }
    public int getIntentosLoginFallidos() { return intentosLoginFallidos; }


    public void setPassword(String password) { this.password = password; }
    public void setAdoptanteId(String adoptanteId) { this.adoptanteId = adoptanteId; }
    public void setEstado(EstadoUsuario estado) { this.estado = estado; }
    public void setUltimoLogin(LocalDateTime ultimoLogin) { this.ultimoLogin = ultimoLogin; }
    public void setIntentosLoginFallidos(int intentosLoginFallidos) {
        this.intentosLoginFallidos = intentosLoginFallidos;
    }

    // para que halla un limite de intentos fallidos
    public void incrementarIntentosFallidos() {
        this.intentosLoginFallidos++;
        if (this.intentosLoginFallidos >= 5) {
            this.estado = EstadoUsuario.BLOQUEADO;
        }
    }

    public void resetearIntentosFallidos() {
        this.intentosLoginFallidos = 0;
    }

    public boolean puedeAutenticar() {
        return this.estado == EstadoUsuario.ACTIVO;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", rol=" + rol +
                ", adoptanteId='" + adoptanteId + '\'' +
                ", estado=" + estado +
                ", fechaCreacion=" + fechaCreacion +
                ", ultimoLogin=" + ultimoLogin +
                ", intentosLoginFallidos=" + intentosLoginFallidos +
                '}';
    }
}
