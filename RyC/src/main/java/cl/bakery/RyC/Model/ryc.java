package cl.bakery.RyC.Model;


import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="RYC")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Todos los productos registrados en la empresa")

public class ryc {
    
    @Id
   @Column(name = "ID_RESENA", nullable = false)
    @Schema(description = "Identificador único de la reseña", example = "1")
    private int id_resena;

    @Column(name = "ID_CLIENTE", nullable = false)
    @Schema(description = "Identificador del cliente que realizó la reseña", example = "2001")
    private int id_cliente;

    @Column(name = "ID_PRODUCTO", nullable = false)
    @Schema(description = "Identificador del producto reseñado", example = "501")
    private int id_producto;

    @Column(name = "CALIFICACION", nullable = false)
    @Schema(description = "Puntaje de la reseña (1 a 5 estrellas)", example = "5")
    private int calificacion;

    @Column(name = "COMENTARIO", nullable = false, length = 500)
    @Schema(description = "Comentario del cliente sobre el producto", example = "Excelente calidad, llegó rápido.")
    private String comentario;

    @Column(name = "FECHA", nullable = false, length = 20)
    @Schema(description = "Fecha en que se realizó la reseña", example = "2025-11-11")
    private String fecha;

    @Column(name = "ESTADO", nullable = false, length = 20)
    @Schema(description = "Estado de la reseña (activa, revisada, eliminada)", example = "activa")
    private String estado;

    @Column(name = "RESENA", nullable = false, length = 100)
    @Schema(description = "Título breve o resumen de la reseña", example = "Gran producto")
    private String resena;

}