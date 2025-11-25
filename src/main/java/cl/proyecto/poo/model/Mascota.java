package cl.proyecto.poo.model;

import java.time.LocalDate;
import java.util.Objects;

public class Mascota {
    private final String id;
    private final String nombre;
    private final Especie especie;
    private final String raza;
    private final Tamano tamano;
    private final LocalDate fechaNacimiento;
    private boolean vacunado;
    private boolean esterilizado;
    private String estadoSalud;
    private boolean adoptada;
    private String adoptanteId;

    public Mascota(String id, String nombre, Especie especie, String raza, Tamano tamano,
                   LocalDate fechaNacimiento, boolean vacunado, boolean esterilizado, String estadoSalud) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.tamano = tamano;
        this.fechaNacimiento = fechaNacimiento;
        this.vacunado = vacunado;
        this.esterilizado = esterilizado;
        this.estadoSalud = estadoSalud;
        this.adoptada = false;
        this.adoptanteId = null;

    }

    public int getEdadAnios() {
        return LocalDate.now().getYear() - fechaNacimiento.getYear();
    }


    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public Especie getEspecie() { return especie; }
    public String getRaza() { return raza; }
    public Tamano getTamano() { return tamano; }
    public boolean isVacunado() { return vacunado; }
    public boolean isEsterilizado() { return esterilizado; }
    public String getEstadoSalud() { return estadoSalud; }

    public boolean isAdoptada() {
        return adoptada;
    }

    public String getAdoptanteId() {
        return adoptanteId;
    }

    public void setVacunado(boolean vacunado) { this.vacunado = vacunado; }
    public void setEsterilizado(boolean esterilizado) { this.esterilizado = esterilizado; }
    public void setEstadoSalud(String estadoSalud) { this.estadoSalud = estadoSalud; }

    public void setAdoptanteId(String adoptanteId) {
        this.adoptanteId = adoptanteId;
    }

    public void setAdoptada(boolean adoptada) {
        this.adoptada = adoptada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mascota)) return false;
        Mascota m = (Mascota) o;
        return Objects.equals(id, m.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Mascota{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", especie=" + especie +
                ", raza='" + raza + '\'' +
                ", tamano=" + tamano +
                ", fechaNacimiento=" + fechaNacimiento +
                ", edadAnios=" + getEdadAnios() +
                ", vacunado=" + vacunado +
                ", esterilizado=" + esterilizado +
                ", estadoSalud='" + estadoSalud + '\'' +
                '}';
    }

}
