package cl.bakery.Usuarios.Assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import cl.bakery.Usuarios.Controller.usuarioController;
import cl.bakery.Usuarios.Model.usuario;

@Component
public class usuarioModelAssembler implements RepresentationModelAssembler<usuario, EntityModel<usuario>>{

    @Override
    public EntityModel<usuario> toModel(usuario u){
        return EntityModel.of(
            u,
            linkTo(methodOn(usuarioController.class).BuscarUsuario(u.getIdUsuario())).withRel("LINKS"),
            /*linkTo(methodOn(usuarioController.class).buscarUsuarioUID(u.getUidFb())).withRel("buscar-usuario-por-UID"),*/
            linkTo(methodOn(usuarioController.class).ListarUsuarios()).withRel("todas-los-Usuario"),
            linkTo(methodOn(usuarioController.class).ActualizarUsuarios(u.getIdUsuario(), u)).withRel("actualiza-una-pruducto")
        );
    }
}