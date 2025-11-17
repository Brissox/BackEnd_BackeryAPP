package cl.bakery.Soporte.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.bakery.Soporte.Model.soporte;

public  interface soporteRepository extends JpaRepository<soporte, Long> {
    List<soporte> findByIdUsuario(Long idUsuario);
    
}
