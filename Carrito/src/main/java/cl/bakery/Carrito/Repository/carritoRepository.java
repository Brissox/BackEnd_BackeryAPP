package cl.bakery.Carrito.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.bakery.Carrito.Model.carrito;

public interface carritoRepository extends JpaRepository<carrito, Long> {
    List<carrito> findByIdUsuario(Long idUsuario);

}