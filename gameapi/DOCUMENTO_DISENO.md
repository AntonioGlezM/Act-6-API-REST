# 📄 Documento de Diseño — GameAPI

> API REST de Videojuegos · Actividad Evaluativa UT6  
> CFGS Desarrollo de Aplicaciones Web (DAW) — CIFP Villa de Agüimes  
> Autores: Antonio González · Yassine Es Sayar

---

## 1. Stack tecnológico y justificación

### Java 17 — Lenguaje principal

Java es el lenguaje enseñado en el módulo de Programación y Acceso a Datos. La versión 17 es LTS (soporte a largo plazo) y es la mínima requerida por Spring Boot 3.x. Ofrece tipado estático, orientación a objetos y un ecosistema maduro con amplia documentación.

### Spring Boot 3.3.5 — Framework backend

Spring Boot simplifica la creación de aplicaciones Spring con configuración automática, servidor embebido (Tomcat) y gestión de dependencias mediante starters. Se eligió sobre alternativas como Quarkus o Micronaut por ser el estándar de la industria y el framework enseñado en clase.

### Spring Data JPA — Capa de persistencia

Proporciona una abstracción sobre JPA/Hibernate que reduce drásticamente el código de acceso a datos. Con `JpaRepository` obtenemos CRUD completo sin escribir consultas SQL. Soporta métodos derivados (`findByNombreContainingIgnoreCase`) y consultas JPQL personalizadas con `@Query`.

### MySQL — Base de datos relacional

MySQL es uno de los sistemas de gestión de bases de datos relacionales más utilizados en la industria y se gestiona cómodamente con MySQL Workbench, herramienta que permite inspeccionar las tablas, ejecutar consultas y ver las relaciones durante la defensa oral. Se eligió por ser un motor real de producción (a diferencia de una base en memoria), lo que hace que el proyecto sea más representativo de un entorno profesional. La base de datos `gamedb` se crea automáticamente al arrancar gracias al parámetro `createDatabaseIfNotExist=true` en la URL de conexión.

### Lombok — Reducción de código boilerplate

Lombok genera automáticamente getters, setters, constructores, `toString()`, `equals()` y `hashCode()` mediante anotaciones como `@Data`, `@Builder`, `@NoArgsConstructor` y `@AllArgsConstructor`. Reduce el código de las entidades un 60-70%.

### JWT (jjwt 0.12.6) — Autenticación sin estado

JSON Web Tokens permite autenticación stateless: el servidor no guarda sesiones, cada petición lleva su token firmado. Se eligió sobre API Key porque es más seguro (los tokens expiran), lleva información del usuario (subject) y es el estándar en APIs REST modernas.

### Spring Security — Framework de seguridad

Spring Security se integra nativamente con Spring Boot y permite configurar reglas de acceso de forma declarativa. Se configuró para que los GET sean públicos y los POST/PUT/DELETE requieran token JWT. Se desactivó CSRF ya que la API es stateless.

### Bean Validation (jakarta.validation) — Validación de datos de entrada

Permite validar los datos recibidos en las peticiones con anotaciones como `@NotBlank`, `@NotNull`, `@Size` y `@Positive` directamente en las entidades. Combinado con `@Valid` en los controladores y `@ControllerAdvice` para el manejo de errores, proporciona respuestas de error claras en JSON.

### SpringDoc OpenAPI (Swagger UI) — Documentación interactiva

Genera automáticamente la documentación de la API a partir de los controladores y la expone en una interfaz web (Swagger UI) donde se pueden probar todos los endpoints. Se configuró un esquema de seguridad JWT para que, mediante el botón **Authorize**, se pueda introducir el token y ejecutar los endpoints protegidos (POST, PUT, DELETE) directamente desde el navegador, sin necesidad de Postman.

---

## 2. Diagrama Entidad-Relación

```
┌──────────────────────┐         ┌──────────────────────────────┐         ┌──────────────────────┐
│      ESTUDIO         │         │           JUEGO              │         │       GENERO         │
├──────────────────────┤         ├──────────────────────────────┤         ├──────────────────────┤
│ id          PK       │───1:N──▶│ id               PK         │◀──N:M──│ id          PK       │
│ nombre      String   │         │ titulo           String     │         │ nombre      String   │
│ pais        String   │         │ descripcion      String     │         │ descripcion String   │
│ anio_fundacion Int   │         │ precio           Double     │         └──────────────────────┘
└──────────────────────┘         │ fecha_lanzamiento LocalDate │
                                 │ estudio_id       FK         │
                                 └──────────────────────────────┘
                                             │
                                             │ N:M
                                             ▼
                                 ┌──────────────────────┐
                                 │    JUEGO_GENERO      │
                                 ├──────────────────────┤
                                 │ juego_id    FK       │
                                 │ genero_id   FK       │
                                 └──────────────────────┘
```

### Descripción de las relaciones

**Estudio → Juego (@OneToMany / @ManyToOne):** Un estudio desarrolla muchos juegos, pero cada juego pertenece a un solo estudio. La FK `estudio_id` está en la tabla `juegos`. `@JsonIgnore` se coloca en el lado `@OneToMany` (`Estudio.juegos`) para evitar recursión infinita al serializar a JSON. `@ToString.Exclude` evita StackOverflow en Lombok.

**Juego ↔ Género (@ManyToMany):** Un juego puede tener varios géneros y un género puede pertenecer a varios juegos. Se usa `@JoinTable` en el lado propietario (`Juego`) para generar la tabla intermedia `juego_genero` con las FKs `juego_id` y `genero_id`. `@JsonIgnore` se coloca en el lado inverso (`Genero.juegos`).

---

## 3. Lista de endpoints

### Autenticación

| Método | Ruta | Descripción | Auth |
|--------|------|-------------|------|
| POST | `/api/v1/auth/login` | Obtener token JWT | No |

**Body:** `{ "username": "admin", "password": "admin123" }`

### Estudios (`/api/v1/estudios`)

| Método | Ruta | Descripción | Auth |
|--------|------|-------------|------|
| GET | `/api/v1/estudios` | Listar todos los estudios | No |
| GET | `/api/v1/estudios/{id}` | Obtener estudio por ID | No |
| GET | `/api/v1/estudios/{id}/juegos` | Juegos de un estudio | No |
| GET | `/api/v1/estudios/buscar?nombre=...&pais=...` | Buscar estudios | No |
| POST | `/api/v1/estudios` | Crear estudio | Sí |
| PUT | `/api/v1/estudios/{id}` | Actualizar estudio | Sí |
| DELETE | `/api/v1/estudios/{id}` | Eliminar estudio | Sí |

### Juegos (`/api/v1/juegos`)

| Método | Ruta | Descripción | Auth |
|--------|------|-------------|------|
| GET | `/api/v1/juegos` | Listar todos los juegos | No |
| GET | `/api/v1/juegos/{id}` | Obtener juego por ID | No |
| GET | `/api/v1/juegos/buscar?titulo=...&precioMax=...&sortBy=id&order=asc` | Buscar con parámetros opcionales | No |
| GET | `/api/v1/juegos/genero/{generoId}` | Juegos de un género (JPQL) | No |
| GET | `/api/v1/juegos/genero/{generoId}/count` | Contar juegos por género (JPQL) | No |
| POST | `/api/v1/juegos` | Crear juego | Sí |
| PUT | `/api/v1/juegos/{id}` | Actualizar juego | Sí |
| PUT | `/api/v1/juegos/{id}/generos` | Asignar géneros a un juego | Sí |
| DELETE | `/api/v1/juegos/{id}` | Eliminar juego | Sí |

### Géneros (`/api/v1/generos`)

| Método | Ruta | Descripción | Auth |
|--------|------|-------------|------|
| GET | `/api/v1/generos` | Listar todos los géneros | No |
| GET | `/api/v1/generos/{id}` | Obtener género por ID | No |
| GET | `/api/v1/generos/buscar?nombre=...` | Buscar por nombre | No |
| POST | `/api/v1/generos` | Crear género | Sí |
| PUT | `/api/v1/generos/{id}` | Actualizar género | Sí |
| DELETE | `/api/v1/generos/{id}` | Eliminar género | Sí |

---

## 4. Decisiones técnicas justificadas

### 4.1 Arquitectura por capas (Controller → Service → Repository)

Se sigue estrictamente el patrón MVC adaptado a Spring Boot. El Controller nunca accede directamente al Repository: siempre pasa por el Service, que encapsula la lógica de negocio. Esto permite cambiar la implementación del repositorio sin modificar el controlador, facilita el testing unitario y respeta el principio de responsabilidad única.

### 4.2 Inyección por constructor (sin @Autowired)

Se usa inyección de dependencias por constructor en lugar de `@Autowired` en campos. Esto hace las dependencias explícitas, permite campos `final` (inmutabilidad), facilita el testing con mocks y es la práctica recomendada por el equipo de Spring.

### 4.3 @JsonIgnore en el lado correcto

`@JsonIgnore` se coloca en `Estudio.juegos` (lado `@OneToMany`) y en `Genero.juegos` (lado inverso de `@ManyToMany`). Sin él, serializar un Estudio incluiría sus Juegos, que a su vez incluirían su Estudio, generando recursión infinita. `@ToString.Exclude` previene el mismo problema en el `toString()` de Lombok.

### 4.4 Optional en findById

Todos los métodos `findById` devuelven `Optional<T>` y se manejan con `.map()` y `.orElse()` en los controladores. Nunca se llama a `.get()` sin comprobar antes. Esto evita `NullPointerException` y devuelve 404 Not Found cuando el recurso no existe.

### 4.5 @Query JPQL vs métodos derivados

Los métodos derivados (`findByTituloContainingIgnoreCase`) se usan para consultas simples que Spring Data resuelve por convención de nombres. Para consultas que navegan relaciones o hacen agregaciones (COUNT de juegos por género), se usa `@Query` con JPQL. JPQL opera sobre las entidades Java (no sobre tablas SQL), lo que lo hace independiente del motor de base de datos.

### 4.6 JWT con filtro personalizado para seguridad

Se implementó JWT con un filtro (`JwtFilter`) que extiende `OncePerRequestFilter`. Se eligió JWT sobre API Key porque los tokens expiran (más seguro), llevan información del usuario (subject) y son el estándar en APIs REST. Se eligió sobre HTTP Basic porque no requiere enviar credenciales en cada petición y es stateless.

### 4.7 @ControllerAdvice + @Valid para calidad

Validación de entrada con `@Valid` y anotaciones como `@NotBlank`, `@NotNull`, `@Size` y `@Positive` en las entidades, y manejo global de excepciones con `@ControllerAdvice` que devuelve respuestas de error consistentes en JSON con timestamp, status, mensaje y campos con errores.

### 4.8 MySQL con datos iniciales en data.sql

Se usa `spring.jpa.defer-datasource-initialization=true` para que Hibernate cree las tablas antes de que se ejecute `data.sql`. El script `data.sql` es idempotente (cada `INSERT` comprueba con `WHERE NOT EXISTS` que la fila no exista ya), de modo que al usar `ddl-auto=update` los datos persisten entre arranques sin generar errores de claves duplicadas.

---

## 5. Capturas de la base de datos

> Las capturas de las tablas generadas se mostrarán en directo durante la defensa oral abriendo el esquema `gamedb` en MySQL Workbench (conexión a `localhost:3306`, usuario: `root`, contraseña: `admin123`).
