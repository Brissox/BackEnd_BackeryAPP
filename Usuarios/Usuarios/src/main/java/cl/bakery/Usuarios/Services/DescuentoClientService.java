package cl.bakery.Usuarios.Services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import cl.bakery.Usuarios.DTO.UsuarioRegistroDescuentoDTO;



@Service
public class DescuentoClientService {

    private final WebClient webClient;

    public DescuentoClientService() {

        String baseUrl = "http://localhost:8089";

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("X-API-KEY", "123456789ABCDEF")
                .build();
    }

    public void asignarDescuentosAlUsuario(Long idUsuario, String codigoRegistro, String correo,
            LocalDate fechaNacimiento) {
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
