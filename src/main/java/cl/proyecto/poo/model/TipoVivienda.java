package cl.proyecto.poo.model;

public enum TipoVivienda {
    CASA_CON_PATIO_GRANDE("Casa con patio grande"),
    CASA_CON_PATIO_PEQUENO("Casa con patio pequeño"),
    CASA_SIN_PATIO("Casa sin patio"),
    DEPARTAMENTO("Departamento"),
    DUPLEX("Dúplex"),
    TOWNHOUSE("Townhouse"),
    SITIO_RURAL("Sitio rural"),
    OTRO("Otro");

    private final String descripcion;

    TipoVivienda(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}