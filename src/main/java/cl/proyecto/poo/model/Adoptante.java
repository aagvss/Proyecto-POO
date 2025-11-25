package cl.proyecto.poo.model;

import java.time.LocalDate;
import java.util.Objects;

public class Adoptante {
    private final String id;
    private final String nombre;
    private final String documento;
    private final LocalDate fechaNacimiento;
    private final TipoVivienda tipoVivienda; // CAMBIAR de String a Enum
    private final boolean propietario;
    private final double ingresosMensuales;
    private final String telefono; // NUEVO CAMPO
    private final String direccion; // NUEVO CAMPO
    private final String ocupacion; // NUEVO CAMPO

    public Adoptante(String id, String nombre, String documento, LocalDate fechaNacimiento,
                     TipoVivienda tipoVivienda, boolean propietario, double ingresosMensuales,
                     String telefono, String direccion, String ocupacion) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoVivienda = tipoVivienda;
        this.propietario = propietario;
        this.ingresosMensuales = ingresosMensuales;
        this.telefono = telefono;
        this.direccion = direccion;
        this.ocupacion = ocupacion;
    }

    public int getEdad() {
        return LocalDate.now().getYear() - fechaNacimiento.getYear();
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDocumento() { return documento; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public TipoVivienda getTipoVivienda() { return tipoVivienda; }
    public boolean isPropietario() { return propietario; }
    public double getIngresosMensuales() { return ingresosMensuales; }
    public String getTelefono() { return telefono; }
    public String getDireccion() { return direccion; }
    public String getOcupacion() { return ocupacion; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Adoptante)) return false;
        Adoptante a = (Adoptante) o;
        return Objects.equals(id, a.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Adoptante{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", documento='" + documento + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", edad=" + getEdad() +
                ", tipoVivienda=" + tipoVivienda +
                ", propietario=" + propietario +
                ", ingresosMensuales=" + ingresosMensuales +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ocupacion='" + ocupacion + '\'' +
                '}';
    }
}
