# Sistema Hogar Responsable
## Descripción
Es una aplicación de escritorio desarrollada en Java diseñada para facilitar y optimizar el proceso de adopción de animales en refugios y organizaciones de rescate animal.

El sistema permite gestionar todo el ciclo de adopción, desde el registro de mascotas disponibles hasta 
la evaluación y aprobación de solicitantes, garantizando que cada animal encuentre un hogar adecuado según criterios predefinidos.

## Características Principales
### Gestion de usuarios y Roles
- Sistema de autenticación seguro con tres tipos de roles: Administrador, Empleado y Adoptante
- Registro de usuarios con validación de datos y encriptación de contraseñas
- Panel administrativo para gestión de usuarios y permisos

### Gestión de Mascotas
- Registro completo de mascotas con infrmación detallada (especie, raza, tamaño, historial médico)
- Control de estado de adopción (disponible/adoptada)
- Interfaz intuitiva para visualización y búsqueda de mascotas diponiblesç

### Proceso de Adopción
- motor de reglas que evalúa automáticamente la compatibilidad entre el adoptante y la mascota
- Validaciones basadas en criterios com edad del adoptante, tipo de vivienda e ingresos económicos
- Seguimiento de solicitudes con estados definidos (aprobada, requiere revisión, rechazada)

### Persistencia de Datos
- Almacenamiento de archivos JSON para todos los datos del sistema


## Tecnologías utilizadas
### Lenguaje y Plataforma
- Java 24
- Swing
- Gson

### Arquitectura y Patrones
- MVC (Modelo-Vista-Controlador)
- Patrón Repository para el acceso a datos
- Patrón Service para la lógica de negocio

### Estructura del Proyecto
```bash
src/
├── main/java/cl/proyecto/poo/
│   ├── model/          # Entidades del sistema
│   ├── repository/     # Acceso a datos y persistencia
│   ├── service/        # Lógica de negocio
│   ├── gui/           # Interfaces gráficas
│   ├── rules/         # Motor de reglas para adopciones
│   └── core/          # Utilidades y configuración
```


