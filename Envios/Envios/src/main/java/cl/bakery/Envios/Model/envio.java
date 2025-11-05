package cl.bakery.Envios.Model;

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
@Data
@Table(name="ENVIOS")
@AllArgsConstructor
@NoArgsConstructor
@Schema(description="Contiene la informacion relacionada con el proceso de envio de productos")

public class envio {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_ENVIO")
    @Schema(description="identificador del envio, se genera automaticamente")
    private Long id_envio;

    @Column(name="TRANSPORTISTA",nullable=false,length=50)
    @Schema(description="nombre de la empresa que transporta el envio",example="starken")
    private String transportista;

    @Column(name="NUMERO_GUIA",nullable=false,precision=10)
    @Schema(description="codigo unico asignado a cada envio", example="111111")
    private int numero_guia;

    @Column(name="FECHA_ENVIO",nullable=false)
    @Schema(description="Fecha en que el paquete se entrega al transportista",example="2025-06-24T15:30:00")
    private LocalDateTime fecha_envio;

    @Column(name="FECHA_ENTREGA",nullable=true)
    @Schema(description="fecha en que el paquete llega fisicamente a su destino final", example="2025-06-24T15:30:00")
    private LocalDateTime fecha_entrega;

    @Column(name="ESTADO",nullable=false,length=20)
    @Schema(description="informacion del progreso del envio", example="en transito/en proceso de envio/entregado")
    private String estado;
    
    @Column(name = "ID_PEDIDO",nullable=false,precision=10)
    @Schema(description="identificador del pedido que se enviara", example="1")
    private Long id_pedido;
    
}
