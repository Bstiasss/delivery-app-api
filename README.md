# DeliveryApp Backend

Backend de una aplicación de delivery desarrollada con Spring Boot que permite la gestión de comercios, productos, pedidos y repartidores mediante autenticación JWT y control de acceso basado en roles.

## Descripción

DeliveryApp Backend expone una API REST para administrar el ciclo completo de un pedido de delivery:

- Los consumidores pueden visualizar comercios y productos, realizar pedidos y consultar su estado.
- Los comerciantes gestionan sus tiendas y productos, además de aceptar o rechazar pedidos.
- Los repartidores visualizan pedidos disponibles, los toman y realizan las entregas.
- Los administradores poseen acceso total para gestionar usuarios, comercios, productos y pedidos.

---

## Tecnologías utilizadas

- Java 21
- Spring Boot 4
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- H2 Database
- Bean Validation
- Lombok
- Swagger / OpenAPI
- Maven
- DataFaker

---

## Arquitectura

El proyecto sigue una arquitectura modular basada en capas.

```text
src/main/java/com.deliveryapp.backend
│
├── common
├── user
├── store
├── product
├── order
│
└── BackendApplication
```

Cada módulo contiene:

```text
controller
service
repository
model
dto
mapper
exception
filter
specification
```

---

## Roles del sistema

### Consumer

- Visualizar comercios
- Visualizar productos
- Crear pedidos
- Consultar estado de pedidos

### Merchant

- Crear comercios
- Gestionar productos de su comercio
- Visualizar pedidos recibidos
- Confirmar pedidos
- Cancelar pedidos
- Marcar pedidos como despachados

### Rider

- Visualizar pedidos disponibles
- Tomar pedidos confirmados
- Actualizar ubicación de entrega
- Completar pedidos

### Administrator

- Gestión completa de usuarios
- Gestión completa de comercios
- Gestión completa de productos
- Gestión completa de pedidos

---

## Modelo de estados de pedido

```text
PENDING
   │
   ├──► CONFIRMED
   │          │
   │          └──► CONFIRMED_ASSIGNED
   │                         │
   │                         └──► DISPATCHED
   │                                      │
   │                                      └──► COMPLETED
   │
   └──► CANCELLED
```

### Significado de cada estado

| Estado | Descripción |
|----------|----------|
| PENDING | Pedido creado y pendiente de revisión |
| CONFIRMED | Pedido aceptado por el comercio |
| CONFIRMED_ASSIGNED | Pedido asignado a un repartidor |
| DISPATCHED | Pedido retirado y en camino |
| COMPLETED | Pedido entregado |
| CANCELLED | Pedido cancelado |

---

## Funcionalidades implementadas

### Stores

- Crear tienda
- Modificar tienda
- Eliminar tienda
- Buscar tiendas
- Filtrar por nombre
- Filtrar por proximidad geográfica

### Products

- Crear producto
- Modificar producto
- Eliminar producto
- Buscar productos
- Filtrar por:
    - Nombre
    - Descripción
    - Estado
    - Comercio
    - Precio
    - Distancia

### Orders

- Crear pedido
- Modificar pedido
- Cancelar pedido
- Confirmar pedido
- Asignar repartidor
- Despachar pedido
- Completar pedido
- Filtrar pedidos por:
    - Estado
    - Consumidor
    - Tipo de pago
    - Total

### Users

- Registro
- Login JWT
- Administración de usuarios
- Gestión de roles

---

## Paginación

Los endpoints de búsqueda soportan paginación.

### Parámetros

| Parámetro | Default |
|------------|------------|
| page | 0 |
| size | 10 |
| sortBy | id |
| direction | asc |

### Ejemplo

```http
GET /api/v1/orders/store/1?page=0&size=10&sortBy=id&direction=asc
```

---

## Filtrado dinámico

El proyecto utiliza Spring Specifications para construir filtros dinámicos.

Ejemplo:

```http
GET /api/v1/orders/store/1?status=CONFIRMED&paymentType=CASH
```

---

## Seguridad

La API utiliza autenticación JWT.

### Flujo

1. Login
2. Generación de token JWT
3. Envío del token en cada request protegida

```http
Authorization: Bearer <token>
```

---

## Base de datos

Actualmente el proyecto utiliza H2 en memoria.

Configuración:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:deliveryappdb
```

### Consola H2

Disponible en:

```text
http://localhost:8080/h2-console
```

---

## Documentación Swagger

Swagger UI disponible en:

```text
http://localhost:8080/swagger
```

Permite:

- Visualizar endpoints
- Ejecutar pruebas
- Inspeccionar DTOs
- Ver respuestas de la API

---

## Datos de prueba

El proyecto incorpora un DataSeeder que genera automáticamente:

- Usuarios
- Comercios
- Productos
- Datos de ejemplo

al iniciar la aplicación.

---

## Instalación

### Clonar repositorio

```bash
git clone <url-del-repositorio>
```

### Ejecutar

```bash
./mvnw spring-boot:run
```

o

```bash
mvn spring-boot:run
```

---

## Mejoras futuras

- Geolocalización en tiempo real de repartidores
- Tracking en vivo de pedidos
- Notificaciones push
- Integración con mapas
- Persistencia en MySQL/PostgreSQL
- Testing automatizado
- Dockerización
- Integración continua (CI/CD)

---

## Equipo

Proyecto académico desarrollado para la Universidad Tecnológica Nacional (UTN).

Desarrolladores:
- Amato Lebron, Tomás
- Lomuoio, Lucas José
- Rossitto, Sofía Elizabeth
- Gomenza, Galo

---

## Versión

```text
API Version: 1.0.0
```
