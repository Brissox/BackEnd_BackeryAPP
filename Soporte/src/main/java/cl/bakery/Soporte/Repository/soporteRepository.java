package cl.bakery.Soporte.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.bakery.Soporte.Model.*;

public  interface soporteRepository extends JpaRepository<soporte, Long> {
    
}
