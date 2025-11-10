package cl.bakery.Dcarrito.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.bakery.Dcarrito.Model.dcarrito;

@Repository
public interface dcarritoRepository extends JpaRepository<dcarrito, Long> {
}
