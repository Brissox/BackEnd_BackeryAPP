package cl.bakery.Carrito.Assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import cl.bakery.Carrito.Controller.carritoController;
import cl.bakery.Carrito.Model.carrito;

@Component
public class carritoModelAssembler implements RepresentationModelAssembler<carrito, EntityModel<carrito>> {

    @Override
    public EntityModel<carrito> toModel(carrito c) {
        return EntityModel.of(
            c,
            linkTo(methodOn(carritoController.class).buscarCarrito(c.getId_carrito())).withRel("LINKS")
           //linkTo(methodOn(carritoController.class).listarTodo()).withRel("todos-los-carritos")
            // Puedes agregar otros enlaces si luego implementas actualizar o eliminar
        );
    }
}
