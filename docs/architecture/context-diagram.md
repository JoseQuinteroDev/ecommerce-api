# Context Diagram (texto)

```text
[Client / Frontend]
      |
      v
[API Gateway]
  |      |      |
  v      v      v
Auth   Catalog  Cart
  \      |      /
   \     v     /
      [Order] -----> [Inventory]
         |
         v
      [Payment]

[Batch Service] --> procesa catálogos/procesos nocturnos
```

## Principios
- Cada servicio tiene su propia base MySQL.
- Comunicación síncrona en checkout por Feign.
- JWT emitido por `auth-service` y validado en servicios que lo requieran.
