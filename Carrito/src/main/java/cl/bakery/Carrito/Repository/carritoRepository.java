package cl.bakery.Carrito.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.bakery.Carrito.Model.*;

public  interface carritoRepository extends JpaRepository<carrito, Long> {
    
}
