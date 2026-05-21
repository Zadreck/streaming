# Streaming CRUD — Spring Boot

**Autor:** José Miguel Rojas Bacco  
**Módulo:** Acceso a Datos  
**Tema asignado:** #13 — Streaming / Serie  
**Relación:** `Serie` → `Episodio` (OneToMany)

---

## Descripción

API REST e interfaz web para gestionar series y sus episodios en una plataforma de streaming.  
Implementa CRUD completo sobre ambos modelos con Spring Boot, JPA, Thymeleaf y MySQL en Docker.

## Cómo ejecutar el proyecto

### Requisitos previos

- [Java 21](https://adoptium.net/) instalado
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) instalado y en ejecución
- Git

### 1. Clonar el repositorio

```bash
git clone https://github.com/joserojasb/streaming.git
cd streaming
```

### 2. Levantar la base de datos con Docker

```bash
docker compose up -d
```

Esto arranca un contenedor MySQL 8.0 con:

| Parámetro | Valor |
|---|---|
| Host | `localhost` |
| Puerto | `3306` |
| Base de datos | `streaming_db` |
| Usuario | `jose` |
| Contraseña | `jose1234` |

Puedes verificar que el contenedor está corriendo con:

```bash
docker ps
```

### 3. Arrancar la aplicación

mvn spring-boot:run

La aplicación arranca en **http://localhost:8080**

---

## Acceso a la interfaz web

| URL | Descripción |
|---|---|
| http://localhost:8080/web/series | Listado de series (con filtro) |
| http://localhost:8080/web/series/nueva | Crear nueva serie |
| http://localhost:8080/web/episodios | Listado de todos los episodios |
| http://localhost:8080/web/episodios/nuevo | Crear nuevo episodio |

---

## 📡 API REST — Endpoints

### Series

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/series` | Listar todas las series |
| `GET` | `/api/series?genero=Drama` | Filtrar por género |
| `GET` | `/api/series?plataforma=Netflix` | Filtrar por plataforma |
| `GET` | `/api/series/{id}` | Obtener serie por ID |
| `POST` | `/api/series` | Crear nueva serie |
| `PUT` | `/api/series/{id}` | Actualizar serie |
| `DELETE` | `/api/series/{id}` | Borrar serie (y sus episodios en cascada) |
| `GET` | `/api/series/{id}/temporada/{t}/duracion` | Duración total (min) de una temporada |

### Episodios

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/episodios` | Listar todos los episodios |
| `GET` | `/api/episodios?serieId=1` | Episodios de una serie |
| `GET` | `/api/episodios?serieId=1&temporada=2` | Episodios de una serie por temporada |
| `GET` | `/api/episodios?desde=2024-01-01&hasta=2024-12-31` | Episodios entre dos fechas |
| `GET` | `/api/episodios/{id}` | Obtener episodio por ID |
| `POST` | `/api/episodios` | Crear nuevo episodio |
| `PUT` | `/api/episodios/{id}` | Actualizar episodio |
| `DELETE` | `/api/episodios/{id}` | Borrar episodio |

### Ejemplo de petición POST para crear una serie

```bash
curl -X POST http://localhost:8080/api/series \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Breaking Bad",
    "genero": "Drama",
    "plataforma": "Netflix",
    "anoEstreno": 2008
  }'
```

### Ejemplo de petición POST para crear un episodio

```bash
curl -X POST http://localhost:8080/api/episodios \
  -H "Content-Type: application/json" \
  -d '{
    "numero": 1,
    "temporada": 1,
    "titulo": "Pilot",
    "duracionMin": 58,
    "fechaEmision": "2008-01-20",
    "serie": { "id": 1 }
  }'
```

---

##  Modelo de datos

### Serie (tabla: `series`)

| Campo | Tipo | Descripción |
|---|---|---|
| id | Long | Clave primaria (auto) |
| titulo | String | Título de la serie |
| genero | String | Género (Drama, Comedia…) |
| plataforma | String | Plataforma (Netflix, HBO…) |
| anoEstreno | int | Año de estreno |

### Episodio (tabla: `episodios`)

| Campo | Tipo | Descripción |
|---|---|---|
| id | Long | Clave primaria (auto) |
| numero | int | Número de episodio |
| temporada | int | Número de temporada |
| titulo | String | Título del episodio |
| duracionMin | int | Duración en minutos |
| fechaEmision | LocalDate | Fecha de emisión |
| serie_id | Long (FK) | Serie a la que pertenece |

**Relación:** una `Serie` tiene muchos `Episodio` (OneToMany). Al borrar una serie se eliminan todos sus episodios en cascada.

---

## Estructura del proyecto

```
src/main/java/joserojasb/streaming/
├── models/
│   ├── Serie.java           (@Entity, @OneToMany)
│   └── Episodio.java        (@Entity, @ManyToOne)
├── repositories/
│   ├── SerieRepository.java
│   └── EpisodioRepository.java
├── services/
│   └── StreamingService.java
└── controllers/
    ├── ApiController.java   (@RestController → /api/...)
    ├── HomeController.java
    └── WebController.java   (@Controller    → /web/...)

src/main/resources/
├── application.properties
└── templates/
    ├── series/
    │   ├── lista.html
    │   ├── nueva.html
    │   ├── editar.html
    │   └── detalle.html
    └── episodios/
        ├── lista.html
        ├── nuevo.html
        └── editar.html
```

---

##  Docker

El archivo `docker-compose.yaml` en la raíz del proyecto define el servicio MySQL.

```bash
# Levantar
docker compose up -d

# Ver logs
docker logs streaming_db

# Parar y eliminar contenedor
docker compose down

# Parar y eliminar contenedor + datos
docker compose down -v
```

---

## ⚙️ Configuración (`application.properties`)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/streaming_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=jose
spring.datasource.password=jose1234
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8080
```

Las tablas se crean automáticamente al arrancar la aplicación (`ddl-auto=update`).

---
