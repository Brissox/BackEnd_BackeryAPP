package cl.bakery.Carrito.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.bakery.Carrito.Model.detalleCarrito;

public interface detalleCarritoRepository extends JpaRepository<detalleCarrito, Long>{
    
}
