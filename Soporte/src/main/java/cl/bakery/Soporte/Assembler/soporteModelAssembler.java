package cl.bakery.Soporte.Assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Component;

import cl.bakery.Soporte.Controller.soporteController;
import cl.bakery.Soporte.Model.soporte;

@Component
public class soporteModelAssembler implements RepresentationModelAssembler<soporte, EntityModel<soporte>> {

    @Override
    public EntityModel<soporte> toModel(soporte s) {
        return EntityModel.of(
            s,
            linkTo(methodOn(soporteController.class).BuscarSoporte(s.getIdSoporte())).withRel("LINKS"),
            linkTo(methodOn(soporteController.class).ListarTodo()).withRel("todos-los-soportes")
        );
    }
}
