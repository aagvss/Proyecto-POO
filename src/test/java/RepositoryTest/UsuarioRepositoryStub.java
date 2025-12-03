package RepositoryTest;

import cl.proyecto.poo.model.Usuario;
import cl.proyecto.poo.repository.UsuarioRepository;
import java.util.Optional;

public class UsuarioRepositoryStub extends UsuarioRepository {
    private boolean existeEmailSimulado = false;
    private boolean saveFueLlamado = false;
    private Usuario usuarioGuardado;

    // Setter para que la prueba controle el resultado
    public void setExisteEmailSimulado(boolean result) {
        this.existeEmailSimulado = result;
    }

    @Override
    public boolean existsByEmail(String email) {
        return existeEmailSimulado;
    }

    @Override
    public void save(Usuario usuario) {
        this.saveFueLlamado = true;
        this.usuarioGuardado = usuario;
    }

    // Métodos de verificación
    public boolean fueLlamadoSave() { return saveFueLlamado; }
    public Usuario obtenerUsuarioGuardado() { return usuarioGuardado; }
}