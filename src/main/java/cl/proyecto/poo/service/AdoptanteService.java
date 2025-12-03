package cl.proyecto.poo.service;

import cl.proyecto.poo.model.Adoptante;
import cl.proyecto.poo.repository.AdoptanteRepository;
import java.util.regex.Pattern;
import java.util.Optional;

public class AdoptanteService {

    // Reglas de Validación:
    // 1. Documento (RUT): XX.XXX.XXX-X (Ej. 18.555.222-K)
    private static final Pattern DOCUMENTO_REGEX = Pattern.compile("^\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dKk]$");
    // 2. Teléfono: Debe contener exactamente 9 dígitos.
    private static final Pattern TELEFONO_REGEX = Pattern.compile("^\\d{9}$");

    private final AdoptanteRepository repo;

    public AdoptanteService(AdoptanteRepository repo) { this.repo = repo; }

    public void registrarAdoptante(Adoptante a) {
        // **NUEVAS VALIDACIONES**

        // 1. Validar formato del documento
        if (!DOCUMENTO_REGEX.matcher(a.getDocumento()).matches()) {
            throw new IllegalArgumentException("El documento debe tener el formato XX.XXX.XXX-X o X.XXX.XXX-X.");
        }

        // 2. Validar longitud del teléfono (9 números)
        if (!TELEFONO_REGEX.matcher(a.getTelefono()).matches()) {
            throw new IllegalArgumentException("El número de teléfono debe tener exactamente 9 dígitos numéricos.");
        }

        if (repo.existsByDocumento(a.getDocumento())) {
            throw new IllegalArgumentException("Documento ya registrado");
        }
        repo.save(a);
    }

    public Optional<Adoptante> buscarPorId(String id) { return repo.findById(id); }
}
