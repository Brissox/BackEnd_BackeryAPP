package cl.bakery.Pagos.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.bakery.Pagos.Model.*;

public  interface pagoRepository extends JpaRepository<pago, Long> {
    List<pago> findByIdPedido(Long idPedido);
}
