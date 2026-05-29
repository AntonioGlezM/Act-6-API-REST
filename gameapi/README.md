# 🎮 GameAPI — API REST de Videojuegos

API REST desarrollada con **Spring Boot** para gestionar un catálogo de videojuegos, estudios de desarrollo y géneros.

Actividad evaluativa UT6 — CFGS Desarrollo de Aplicaciones Web (DAW).

## 🛠️ Stack tecnológico

- **Java 17** — Lenguaje principal
- **Spring Boot 3.3.5** — Framework backend
- **Spring Data JPA** — Persistencia con Hibernate
- **MySQL** — Base de datos relacional (gestionada con MySQL Workbench)
- **Lombok** — Reducción de boilerplate
- **JWT (jjwt 0.12.6)** — Autenticación basada en tokens
- **Spring Security** — Seguridad en endpoints de escritura
- **Bean Validation** — Validación de datos de entrada
- **SpringDoc OpenAPI (Swagger UI)** — Documentación e interfaz interactiva de la API

## 🚀 Cómo arrancar el proyecto

```bash
# Clonar el repositorio
git clone https://github.com/TU_USUARIO/gameapi.git
cd gameapi

# Arrancar la aplicación
./mvnw spring-boot:run
```

La aplicación arrancará en `http://localhost:8080`.

> **Requisito previo:** tener MySQL en marcha con un usuario `root` y contraseña `admin123`. La base de datos `gamedb` se crea automáticamente al arrancar (gracias al parámetro `createDatabaseIfNotExist=true`). Puedes inspeccionarla con MySQL Workbench.

## 📂 Estructura del proyecto

```
com.antonioyassine.gameapi
├── model/          → Entidades JPA (Estudio, Juego, Genero)
├── repository/     → Repositorios JPA
├── service/        → Lógica de negocio
├── controller/     → Controladores REST
├── security/       → JWT y configuración de seguridad
├── config/         → Configuración de Swagger/OpenAPI
├── exception/      → Manejo global de excepciones
└── GameapiApplication.java
```

## 🔌 Endpoints de la API

### Autenticación
| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/v1/auth/login` | Obtener token JWT |

**Credenciales:** `{ "username": "admin", "password": "admin123" }`

### Estudios (`/api/v1/estudios`)
| Método | Ruta | Descripción | Auth |
|--------|------|-------------|------|
| GET | `/api/v1/estudios` | Listar todos | No |
| GET | `/api/v1/estudios/{id}` | Obtener por ID | No |
| GET | `/api/v1/estudios/{id}/juegos` | Juegos de un estudio | No |
| GET | `/api/v1/estudios/buscar?nombre=...&pais=...` | Buscar | No |
| POST | `/api/v1/estudios` | Crear estudio | Sí |
| PUT | `/api/v1/estudios/{id}` | Actualizar estudio | Sí |
| DELETE | `/api/v1/estudios/{id}` | Eliminar estudio | Sí |

### Juegos (`/api/v1/juegos`)
| Método | Ruta | Descripción | Auth |
|--------|------|-------------|------|
| GET | `/api/v1/juegos` | Listar todos | No |
| GET | `/api/v1/juegos/{id}` | Obtener por ID | No |
| GET | `/api/v1/juegos/buscar?titulo=...&precioMax=...&sortBy=id&order=asc` | Buscar | No |
| GET | `/api/v1/juegos/genero/{generoId}` | Juegos por género | No |
| GET | `/api/v1/juegos/genero/{generoId}/count` | Contar juegos por género | No |
| POST | `/api/v1/juegos` | Crear juego | Sí |
| PUT | `/api/v1/juegos/{id}` | Actualizar juego | Sí |
| PUT | `/api/v1/juegos/{id}/generos` | Asignar géneros | Sí |
| DELETE | `/api/v1/juegos/{id}` | Eliminar juego | Sí |

### Géneros (`/api/v1/generos`)
| Método | Ruta | Descripción | Auth |
|--------|------|-------------|------|
| GET | `/api/v1/generos` | Listar todos | No |
| GET | `/api/v1/generos/{id}` | Obtener por ID | No |
| GET | `/api/v1/generos/buscar?nombre=...` | Buscar | No |
| POST | `/api/v1/generos` | Crear género | Sí |
| PUT | `/api/v1/generos/{id}` | Actualizar género | Sí |
| DELETE | `/api/v1/generos/{id}` | Eliminar género | Sí |

## 🗄️ Base de datos

- **Motor:** MySQL
- **Esquema:** `gamedb` (se crea automáticamente al arrancar)
- **JDBC URL:** `jdbc:mysql://localhost:3306/gamedb`
- **Usuario:** `root` | **Contraseña:** `admin123`
- **Inspección:** abrir el esquema `gamedb` en MySQL Workbench

## 🔒 Seguridad

- Los endpoints **GET** son públicos
- Los endpoints **POST, PUT, DELETE** requieren token JWT
- Para obtener un token: `POST /api/v1/auth/login`

**Ejemplo con Postman:**
1. Hacer POST a `/api/v1/auth/login` con el body `{ "username": "admin", "password": "admin123" }`
2. Copiar el `token` de la respuesta
3. En las peticiones protegidas, añadir header: `Authorization: Bearer <token>`

## 📖 Swagger UI

Documentación interactiva de la API en `http://localhost:8080/swagger-ui.html`. Desde ahí puedes probar todos los endpoints y usar el botón **Authorize** (arriba a la derecha) para introducir el token JWT y ejecutar POST, PUT y DELETE.

## 🌐 Interfaz web

Acceder a `http://localhost:8080` para ver el catálogo de videojuegos en una página HTML que consume la API con `fetch`.

## 👥 Autores

- Antonio González
- Yassine [Apellido]

---

*CIFP Villa de Agüimes — DAW — UT6*
