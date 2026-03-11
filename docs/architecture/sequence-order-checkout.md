# Secuencia checkout
1. Cliente llama `POST /order/orders/checkout`.
2. Order valida carrito en cart-service.
3. Order reserva stock en inventory-service.
4. Order inicia pago en payment-service.
5. Si pago ok, confirma pedido y reserva.
