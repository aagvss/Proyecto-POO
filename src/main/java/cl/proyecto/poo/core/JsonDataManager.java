package cl.proyecto.poo.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.*; // Importante para copiar archivos
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JsonDataManager {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    private static final String DATA_DIR = "data/";
    private static final String BACKUP_DIR = "data/backup/";

    static {
        // Crear directorios si no existen
        new File(DATA_DIR).mkdirs();
        new File(BACKUP_DIR).mkdirs();
    }

    public static <T> void guardarDatos(String archivo, List<T> datos) {
        String rutaPrincipal = DATA_DIR + archivo;
        String rutaBackup = BACKUP_DIR + archivo;

        // Guardar en el archivo principal
        try (Writer writer = new FileWriter(rutaPrincipal, StandardCharsets.UTF_8)) {
            gson.toJson(datos, writer);

            // Si se escribio con exito se actualiza el backup
            crearCopiaDeSeguridad(rutaPrincipal, rutaBackup);

        } catch (IOException e) {
            System.err.println("Error crítico guardando datos en " + archivo + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static <T> List<T> cargarDatos(String archivo, Class<T> tipo) {
        File file = new File(DATA_DIR + archivo);
        File backupFile = new File(BACKUP_DIR + archivo);

        if (!file.exists() && backupFile.exists()) {
            System.out.println("ALERTA: Archivo principal " + archivo + " perdido. Restaurando desde backup...");
            restaurarDesdeBackup(backupFile, file);
        }

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            BufferedReader br = new BufferedReader(reader);
            if (br.readLine() == null) return new ArrayList<>();

            try (Reader jsonReader = new FileReader(file, StandardCharsets.UTF_8)) {
                Type listType = TypeToken.getParameterized(List.class, tipo).getType();
                List<T> datos = gson.fromJson(jsonReader, listType);
                return datos != null ? datos : new ArrayList<>();
            }

        } catch (Exception e) {
            System.err.println("Error: Archivo " + archivo + " corrupto. Intentando recuperar backup...");

            if (backupFile.exists()) {
                restaurarDesdeBackup(backupFile, file);
                return cargarDatos(archivo, tipo);
            }

            return new ArrayList<>();
        }
    }

    private static void crearCopiaDeSeguridad(String origen, String destino) {
        try {
            Path source = Paths.get(origen);
            Path target = Paths.get(destino);
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error creando backup: " + e.getMessage());
        }
    }

    private static void restaurarDesdeBackup(File backup, File destino) {
        try {
            Files.copy(backup.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("RESTAURACIÓN EXITOSA: " + destino.getName() + " recuperado desde backup.");
        } catch (IOException e) {
            System.err.println("FALLO CRÍTICO: No se pudo restaurar el backup: " + e.getMessage());
        }
    }

    private static class LocalDateAdapter extends TypeAdapter<LocalDate> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            if (value == null) { out.nullValue(); return; }
            out.value(value.format(formatter));
        }
        @Override
        public LocalDate read(JsonReader in) throws IOException {
            if (in.peek() == com.google.gson.stream.JsonToken.NULL) { in.nextNull(); return null; }
            return LocalDate.parse(in.nextString(), formatter);
        }
    }

    private static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        @Override
        public void write(JsonWriter out, LocalDateTime value) throws IOException {
            if (value == null) { out.nullValue(); return; }
            out.value(value.format(formatter));
        }
        @Override
        public LocalDateTime read(JsonReader in) throws IOException {
            if (in.peek() == com.google.gson.stream.JsonToken.NULL) { in.nextNull(); return null; }
            return LocalDateTime.parse(in.nextString(), formatter);
        }
    }
}