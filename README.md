# CommerceHub — E-commerce Backend (Spring Microservices)

Backend e-commerce **portfolio junior-mid con enfoque enterprise**: dominio separado por microservicio, seguridad JWT realista, flujo de checkout orquestado y procesos batch.

## Stack
- Java 21, Spring Boot 3, Spring Cloud (Gateway, Eureka, Config, OpenFeign)
- Spring Security + JWT access token + refresh token persistido (hasheado)
- Spring Batch
- Flyway + MySQL (una base por servicio)
- Docker Compose
- Springdoc OpenAPI

## Arquitectura
### Infraestructura
- `config-server`
- `discovery-service`
- `api-gateway`

### Microservicios de negocio
- `auth-service`
- `customer-service`
- `catalog-service`
- `inventory-service`
- `cart-service`
- `order-service`
- `payment-service`
- `batch-service`

## Capacidades profesionales incluidas
- **Auth realista**
  - Registro/login/refresh/logout/me
  - Password hash con BCrypt
  - Refresh token persistido y guardado hasheado
  - Filtro JWT stateless para endpoints protegidos
- **Order checkout orquestado**
  - Snapshot de items en `order_item`
  - Integración síncrona con `inventory-service` y `payment-service` usando Feign
  - Estados de pedido y validación de cancelación
- **Errores estandarizados por servicio**
  - `timestamp`, `service`, `path`, `code`, `message`, `traceId`
- **Batch base enterprise**
  - `CatalogImportJob` lanzable por API

## Endpoints principales
### Auth (`auth-service`)
- `POST /auth/register`
- `POST /auth/login`
- `POST /auth/refresh`
- `POST /auth/logout`
- `GET /auth/me`

### Orders (`order-service`)
- `POST /order/orders/checkout`
- `GET /order/orders/me?customerId={id}`
- `GET /order/orders/{orderId}`
- `PATCH /order/orders/{orderId}/cancel`

### Batch (`batch-service`)
- `POST /batch/jobs/catalog-import/run`

## Estructura
- `services/*`: microservicios
- `docker/mysql/*`: init de MySQL por dominio
- `config-repo/*`: configuración central para Spring Cloud Config
- `docs/architecture/*`: diagramas y secuencias de negocio
- `scripts/*`: utilidades de entorno local

## Run local
```bash
mvn -q -DskipTests package
./scripts/up.sh
```

## Roadmap próximo (enterprise)
1. Añadir roles/permisos (RBAC) y method security fina.
2. Completar entidades de `catalog`, `inventory`, `payment`, `cart`.
3. Introducir outbox + eventos (RabbitMQ/Kafka) para post-checkout.
4. Añadir tests con Testcontainers por servicio crítico.
5. Observabilidad completa (trazas + métricas + dashboards).
