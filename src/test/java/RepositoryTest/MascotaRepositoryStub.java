package RepositoryTest;

import cl.proyecto.poo.model.Mascota;
import cl.proyecto.poo.repository.MascotaRepository;
import java.util.*;

// Extiende el Repositorio (asumiendo que los métodos de persistencia son públicos/protegidos)
public class MascotaRepositoryStub extends MascotaRepository {
    private boolean saveFueLlamado = false;
    private Mascota mascotaGuardada;

    // Sobreescribe el metodo save para capturar la llamada
    @Override
    public void save(Mascota m) {
        this.saveFueLlamado = true;
        this.mascotaGuardada = m;
        // NO LLAMA A LA LÓGICA DE GUARDADO REAL (JSON)
    }

    // Métodos de verificación para la prueba
    public boolean fueLlamadoSave() { return saveFueLlamado; }
    public Mascota obtenerMascotaGuardada() { return mascotaGuardada; }

    // Implementación mínima para evitar errores si otros métodos son llamados
    @Override
    public Optional<Mascota> findById(String id) { return Optional.empty(); }
}