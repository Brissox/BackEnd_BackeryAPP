package cl.bakery.RyC.Model;


import java.time.LocalDate;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESENA")
    private Long idResena;

    @Column(name = "ID_CLIENTE", nullable = false)
    private Long idCliente;

    @Column(name = "ID_PRODUCTO", nullable = false)
    private Long idProducto;

    @Column(name = "CALIFICACION", nullable = false)
    private Integer calificacion;

    @Column(name = "COMENTARIO", length = 500)
    private String comentario;

    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha;

    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado;

    @Column(name = "RESENA", length = 255)
    private String resena;

}