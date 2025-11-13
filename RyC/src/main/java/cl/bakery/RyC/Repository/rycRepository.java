package cl.bakery.RyC.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.bakery.RyC.Model.*;

public  interface rycRepository extends JpaRepository<ryc, Long> {
    List<ryc> findByIdUsuario(Long idUsuario);
    
}