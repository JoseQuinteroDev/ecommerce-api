# CommerceHub — e-commerce backend con microservicios (Spring)

Monorepo profesional para portfolio junior-mid: Spring Boot + Spring Cloud + JWT + Batch + OpenAPI + MySQL + Docker Compose.

## Arquitectura
- Infraestructura: `config-server`, `discovery-service`, `api-gateway`.
- Negocio: `auth`, `customer`, `catalog`, `inventory`, `cart`, `order`, `payment`, `batch`.
- DB por servicio (aislamiento real).

## Endpoints MVP
- Auth: `/auth/register`, `/auth/login`, `/auth/refresh`, `/auth/logout`, `/auth/me`
- Catalog: `/catalog/products`, `/catalog/categories`
- Cart: `/cart/carts/me`
- Orders: `/order/orders/checkout`
- Payments: `/payment/payments`
- Batch: `/batch/jobs/catalog-import/run`

## Ejecutar
```bash
mvn -q -DskipTests package
docker compose up -d --build
```

## Diseño técnico destacado
- JWT stateless con refresh token en `auth-service`.
- Estructura package-by-feature en servicios.
- Spring Batch con `CatalogImportJob` lanzable por endpoint admin.
- Swagger/OpenAPI en cada microservicio (`/swagger-ui/index.html`).
- Flyway habilitado por servicio para migraciones.

## Roadmap
1. Completar entidades JPA del dominio (UserAccount, ProductVariant, StockReservation, OrderItem, PaymentTransaction, etc.).
2. Integrar Feign + Resilience4j en checkout real.
3. Añadir tests unitarios, integración y Testcontainers.
4. Añadir observabilidad (Prometheus/Grafana + trazas).
