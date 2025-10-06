package cl.proyecto.poo.model;

import java.time.LocalDate;
import java.util.Objects;

public class Adoptante {
    private final String id;
    private final String nombre;
    private final String documento; // ej. RUT o DNI
    private final LocalDate fechaNacimiento;
    private final String tipoVivienda; // e.g. "departamento", "casa_con_patio"
    private final boolean propietario;
    private final double ingresosMensuales;

    public Adoptante(String id, String nombre, String documento, LocalDate fechaNacimiento,
                     String tipoVivienda, boolean propietario, double ingresosMensuales) {

        if (ingresosMensuales < 0) {
            throw new IllegalArgumentException("Ingresos no pueden ser negativos");
        }
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoVivienda = tipoVivienda;
        this.propietario = propietario;
        this.ingresosMensuales = ingresosMensuales;
    }

    public int getEdad() {
        return LocalDate.now().getYear() - fechaNacimiento.getYear();
    }

    // getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDocumento() { return documento; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getTipoVivienda() { return tipoVivienda; }
    public boolean isPropietario() { return propietario; }
    public double getIngresosMensuales() { return ingresosMensuales; }

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
                ", tipoVivienda='" + tipoVivienda + '\'' +
                ", propietario=" + propietario +
                ", ingresosMensuales=" + ingresosMensuales +
                '}';
    }

}
