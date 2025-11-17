package cl.bakery.Usuarios.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import  cl.bakery.Usuarios.Model.usuario;

public interface usuarioRepository extends JpaRepository<usuario, Long>{

    usuario findByIdUID(String UID_FB);
    
}


