# Lógica de Asignación de Descuentos

Este documento describe la arquitectura y la lógica recomendada para integrar el microservicio `Usuarios` con el microservicio `Descuentos`.

**Objetivo:** que el servicio `Usuarios` delegue la lógica de negocio de asignación de descuentos al servicio `Descuentos`, evitando duplicar reglas y manteniendo separación de responsabilidades.

---

**Arquitectura propuesta (resumen)**

Usuarios (puerto p.ej. 8081)  --HTTP-->  Descuentos (puerto p.ej. 8082)

Flujo mínimo:
- El usuario se registra en `Usuarios`.
- `Usuarios` guarda el usuario en su BD y obtiene `idUsuario`.
- `Usuarios` hace `POST /descuentos/asignar` enviando un DTO con: `idUsuario`, `codigoRegistro`, `correo`, `fechaNacimiento`.
- `Descuentos` aplica las reglas y persiste los descuentos asignados (tabla `usuario_descuento`).

---

**Patrón recomendado:** Delegado (sync HTTP) — el servicio `Descuentos` es responsable de evaluar reglas y persistir asignaciones.

Ventajas:
- Separación clara de responsabilidades.
- Fácil mantenimiento y pruebas (Descuentos contiene la lógica de negocio).
- Si `Descuentos` falla, el registro del usuario no debe fallar (tolerancia parcial).

Consideraciones opcionales:
- Para alta disponibilidad y desacoplamiento absoluto, usar eventos (Kafka/RabbitMQ) en vez de llamadas síncronas.
- Implementar `retry` y `circuit breaker` (Resilience4j / Spring Cloud Circuit Breaker) si la llamada síncrona es crítica.

---

**Contrato de comunicación (DTO)**

En `Usuarios` y `Descuentos` debe existir un DTO compartido (o equivalente) con estas propiedades mínimas:

```java
public class UsuarioRegistroDescuentoDTO {
	private Integer idUsuario;
	private String codigoRegistro;
	private String correo;
	private java.time.LocalDate fechaNacimiento;
	// getters/setters
}
```

---

**Endpoints (Descuentos)**

- POST /descuentos/asignar
  - Cuerpo: `UsuarioRegistroDescuentoDTO`
  - Respuesta: 200 OK (o 4xx/5xx en caso de error de validación / servidor)

---

**Implementación sugerida - `Usuarios`**

1) Cliente HTTP (preferible: `WebClient`):

```java
@Service
public class DescuentoClientService {
	@Value("${descuentos.service.url:http://localhost:8082}")
	private String descuentosServiceUrl;

	private final WebClient webClient;

	public DescuentoClientService(WebClient.Builder builder) {
		this.webClient = builder.baseUrl(descuentosServiceUrl).build();
	}

	public void asignarDescuentosAlUsuario(Integer idUsuario, String codigoRegistro, String correo, LocalDate fechaNacimiento) {
		UsuarioRegistroDescuentoDTO dto = new UsuarioRegistroDescuentoDTO();
		dto.setIdUsuario(idUsuario);
		dto.setCodigoRegistro(codigoRegistro);
		dto.setCorreo(correo);
		dto.setFechaNacimiento(fechaNacimiento);

		try {
			webClient.post()
				.uri("/descuentos/asignar")
				.bodyValue(dto)
				.retrieve()
				.toBodilessEntity()
				.block(); // bloquear breve para compatibilidad con flujo actual
		} catch (Exception e) {
			// No frenar el registro de usuario si la asignación de descuentos falla
			// Loguear y continuar
		}
	}
}
```

2) `usuarioController.registrarUsuario()` — después de guardar en BD:

```java
usuario u = usuarioservices.GuardarUsuario(usuarioNuevo);
try {
	descuentoClientService.asignarDescuentosAlUsuario(
		Math.toIntExact(u.getIdUsuario()),
		usuarioNuevo.getCodigoRegistro(),
		usuarioNuevo.getCorreo(),
		usuarioNuevo.getFechaNacimiento() == null ? null : usuarioNuevo.getFechaNacimiento().toLocalDate()
	);
} catch (Exception ex) {
	// log warning
}
```

Nota: usar `block()` en `WebClient` convierte la llamada a síncrona; es aceptable si la latencia es baja y el servicio es confiable. Si prefieres no bloquear, usa el flujo reactivo y devuelve respuesta antes de que termine la asignación.

---

**Implementación sugerida - `Descuentos`**

Controlador (ya presente):

```java
@PostMapping("/asignar")
public ResponseEntity<?> asignarDescuentos(@RequestBody UsuarioRegistroDescuentoDTO dto) {
	service.asignarDescuentosPorReglas(dto);
	return ResponseEntity.ok("Descuentos asignados");
}
```

Servicio (ejemplo):

```java
public void asignarDescuentosPorReglas(UsuarioRegistroDescuentoDTO dto) {
	// REGLA 1: edad > 50
	if (dto.getFechaNacimiento() != null) {
		int edad = Period.between(dto.getFechaNacimiento(), LocalDate.now()).getYears();
		if (edad > 50) asignar(dto.getIdUsuario(), "ADULTO_MAYOR");
	}

	// REGLA 2: codigo FELICES50
	if ("FELICES50".equals(dto.getCodigoRegistro())) asignar(dto.getIdUsuario(), "FELICES50");

	// REGLA 3: correo DUOC
	if (dto.getCorreo() != null && dto.getCorreo().endsWith("@duocuc.cl")) assign(dto.getIdUsuario(), "DUOC_BDAY");
}

private void asignar(Integer idUsuario, String codigoDescuento) {
	descuento d = descuentoRepository.findByCodigo(codigoDescuento);
	if (d == null) return; // log
	UsuarioDescuento ud = new UsuarioDescuento();
	ud.setIdUsuario(idUsuario);
	ud.setIdDescuento(d.getId());
	ud.setFechaAsignacion(LocalDate.now());
	ud.setActivo("S");
	usuarioDescuentoRepository.save(ud);
}
```

---

**Configuración mínima**

`Usuarios` application.properties:

```properties
descuentos.service.url=http://localhost:8082
```

`Descuentos` application.properties:

```properties
server.port=8082
```

---

**Mejoras recomendadas (futuro)**

- Añadir `retry` con backoff y `circuit-breaker` para llamadas entre servicios.
- Cambiar a arquitectura event-driven para desacoplamiento total.
- Añadir métricas y trazas (OpenTelemetry, Zipkin) para seguimiento de flujos entre servicios.
- Validaciones y contratos compartidos (p. ej. OpenAPI contract o librería DTO común).

---

Si quieres, puedo:

- Implementar el cliente `WebClient` y el llamado desde `usuarioController` (lo hice antes, pero lo revertiste — puedo volver a agregarlo).
- Añadir pruebas unitarias o de integración para el flujo.
- Implementar retry/circuit-breaker.

Dime cuál de estas acciones quieres que haga ahora.
