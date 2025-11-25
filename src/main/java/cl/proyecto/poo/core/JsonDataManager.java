package cl.proyecto.poo.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;


import java.io.*;
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

    static {
        new File(DATA_DIR).mkdirs();
    }

    public static <T> void guardarDatos(String archivo, List<T> datos) {
        try (Writer writer = new FileWriter(DATA_DIR + archivo, StandardCharsets.UTF_8)) {
            gson.toJson(datos, writer);
        } catch (IOException e) {
            System.err.println("Error guardando datos: " + e.getMessage());
        }
    }

    public static <T> List<T> cargarDatos(String archivo, Class<T> tipo) {
        File file = new File(DATA_DIR + archivo);

        // Si el archivo no existe, retornar lista vacía
        if (!file.exists()) {
            return new ArrayList<>();
        }

        // Si el archivo está vacío, retornar lista vacía
        if (file.length() == 0) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(file, StandardCharsets.UTF_8)) {

            BufferedReader bufferedReader = new BufferedReader(reader);
            String firstLine = bufferedReader.readLine();
            if (firstLine == null || firstLine.trim().isEmpty()) {
                return new ArrayList<>();
            }

            // Si llegamos aquí, hay contenido, resetear el reader
            try (Reader newReader = new FileReader(file, StandardCharsets.UTF_8)) {
                Type listType = TypeToken.getParameterized(List.class, tipo).getType();
                List<T> datos = gson.fromJson(newReader, listType);
                return datos != null ? datos : new ArrayList<>();
            }

        } catch (IOException e) {
            System.err.println("Error cargando datos desde " + archivo + ": " + e.getMessage());
            // Si hay error, eliminar el archivo corrupto y crear uno nuevo
            file.delete();
            return new ArrayList<>();
        }
    }

    private static class LocalDateAdapter extends TypeAdapter<LocalDate> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            out.value(value.format(formatter));
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            String dateString = in.nextString();
            return LocalDate.parse(dateString, formatter);
        }
    }

    private static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        @Override
        public void write(JsonWriter out, LocalDateTime value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            out.value(value.format(formatter));
        }

        @Override
        public LocalDateTime read(JsonReader in) throws IOException {
            if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            String dateTimeString = in.nextString();
            return LocalDateTime.parse(dateTimeString, formatter);
        }
    }
}