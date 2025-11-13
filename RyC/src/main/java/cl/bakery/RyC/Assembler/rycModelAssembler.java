package cl.bakery.RyC.Assembler; 


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import cl.bakery.RyC.Controller.rycController;
import cl.bakery.RyC.Model.ryc;



@Component

public class rycModelAssembler implements RepresentationModelAssembler<ryc, EntityModel<ryc>>{   
     @Override
    public EntityModel<ryc> toModel(ryc p){
        return EntityModel.of(
            p,
            linkTo(methodOn(rycController.class).BuscarUnRYC(p.getId_resena())).withRel("LINKS"),
            linkTo(methodOn(rycController.class).ListarRYC()).withRel("todas-los-productos")
           /* linkTo(methodOn(rycController.class).ActualizarProducto(p.getId_producto(), p)).withRel("actualiza-una-venta")*/ 
        );
    }
}
    
