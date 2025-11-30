package cl.bakery.Pedidos.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.bakery.Pedidos.DTO.CrearPedidoDTO;
import cl.bakery.Pedidos.DTO.DetallePedidoDTO;
import cl.bakery.Pedidos.DTO.ProductoDTO;
import cl.bakery.Pedidos.Model.ItemPedido;
import cl.bakery.Pedidos.Model.Pedido;
import cl.bakery.Pedidos.Repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoClientService productosClient;

    public PedidoService(PedidoRepository pedidoRepository, ProductoClientService productosClient) {
        this.pedidoRepository = pedidoRepository;
        this.productosClient = productosClient;
    }

    @Transactional
    public Pedido crearPedido(CrearPedidoDTO dto) {

        // Crear el pedido
        Pedido pedido = new Pedido();
        pedido.setIdUsuario(dto.getIdUsuario() != null ? dto.getIdUsuario() : 0L);
        pedido.setCantidadProductos(dto.getCantidad_productos());
        pedido.setMetodoPago(dto.getMetodo_de_pago());
        pedido.setDescuentos(dto.getDescuentos());

        List<ItemPedido> items = new ArrayList<>();
        int total = 0;

        // Recorrer detalles
        for (DetallePedidoDTO det : dto.getDetalles()) {

            // Llamar al microservicio de productos
            ProductoDTO producto = productosClient.obtenerProducto(det.getIdProducto());

            if (producto.getStock() < det.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para " + producto.getNombre());

            }

            // Descontar stock en el microservicio de productos
            productosClient.descontarStock(det.getIdProducto(), det.getCantidad());

            // Calcular precio y subtotal
            int precioUnitario = producto.getPrecio();
            int subtotal = precioUnitario * det.getCantidad();
            total += subtotal;

            // Crear item del pedido
            ItemPedido item = new ItemPedido();
            item.setIdProducto(det.getIdProducto());
            item.setCantidad(det.getCantidad());
            item.setNombreProducto(producto.getNombre());
            item.setPrecioUnitario(precioUnitario);
            item.setSubtotal(subtotal);
            item.setPedido(pedido);

            items.add(item);
        }

        // Aplicar descuentos si vienen
        if (dto.getDescuentos() != null) {
            total -= dto.getDescuentos();
            if (total < 0)
                total = 0; // evitar totales negativos
        }

        // Asignar totales e ítems
        pedido.setTotal(total);
        pedido.setItems(items);

        // Guardar el pedido
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    @Transactional
    public List<Pedido> obtenerPorUsuario(Long idUsuario) {
        return pedidoRepository.findByIdUsuario(idUsuario);
    }

    @Transactional
    public Pedido obtenerPedidoPorId(Long idPedido) {
        return pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + idPedido));
    }

}
