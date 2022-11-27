package human.resource.mgmt.api;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import human.resource.mgmt.query.*;

@RestController
public class VacationStatusQueryController {

  private final QueryGateway queryGateway;

  public VacationStatusQueryController(QueryGateway queryGateway) {
      this.queryGateway = queryGateway;
  }
  

  @GetMapping("/vacationStatuses")
  public CompletableFuture findAll() {
      return queryGateway.query(new VacationStatusQuery(), ResponseTypes.multipleInstancesOf(VacationStatus.class))
              .thenApply(resources -> {
                CollectionModel<VacationStatus> model = CollectionModel.of(resources);
                
                return new ResponseEntity<>(model, HttpStatus.OK);
            });

  }

  @GetMapping("/orders/{id}")
  public CompletableFuture findById(@PathVariable("id") Long id) {
    VacationStatusSingleQuery query = new VacationStatusSingleQuery();
    query.setId(id);

      return queryGateway.query(query, ResponseTypes.optionalInstanceOf(VacationStatus.class))
              .thenApply(resource -> {
                if(!resource.isPresent()){
                  return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }

                EntityModel<VacationStatus> model = EntityModel.of(resource.get());
                model
                      .add(Link.of("/vacationStatuses/" + resource.get().getId()).withSelfRel());
              
                return new ResponseEntity<>(model, HttpStatus.OK);
            }).exceptionally(ex ->{
              throw new RuntimeException(ex);
            });

  }



}

