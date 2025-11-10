package cl.bakery.RyC.Assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Component;

import cl.bakery.RyC.Controller.rycController;

import cl.bakery.RyC.Model.ryc;

@Component
public class rycModelAssembler implements RepresentationModelAssembler<ryc, EntityModel<ryc>> {

    @Override
    public EntityModel<ryc> toModel(ryc r) {
        return EntityModel.of(
            r,
            linkTo(methodOn(rycController.class).BuscarRyc(r.getIdResena())).withRel("LINKS"),
            linkTo(methodOn(rycController.class).ListarTodo()).withRel("todas-las-resenas")
        );
    }
}
