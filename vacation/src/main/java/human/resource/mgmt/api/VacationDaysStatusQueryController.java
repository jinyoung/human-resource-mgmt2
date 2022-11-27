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
public class VacationDaysStatusQueryController {

  private final QueryGateway queryGateway;

  public VacationDaysStatusQueryController(QueryGateway queryGateway) {
      this.queryGateway = queryGateway;
  }
  

  @GetMapping("/vacationDaysStatuses")
  public CompletableFuture findAll() {
      return queryGateway.query(new VacationDaysStatusQuery(), ResponseTypes.multipleInstancesOf(VacationDaysStatus.class))
              .thenApply(resources -> {
                CollectionModel<VacationDaysStatus> model = CollectionModel.of(resources);
                
                return new ResponseEntity<>(model, HttpStatus.OK);
            });

  }

  @GetMapping("/vacationDaysStatuses/{id}")
  public CompletableFuture findById(@PathVariable("id") String id) {
    VacationDaysStatusSingleQuery query = new VacationDaysStatusSingleQuery();
    query.setUserId(id);

      return queryGateway.query(query, ResponseTypes.optionalInstanceOf(VacationDaysStatus.class))
              .thenApply(resource -> {
                if(!resource.isPresent()){
                  return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }

                EntityModel<VacationDaysStatus> model = EntityModel.of(resource.get());
                model
                      .add(Link.of("/vacationDaysStatuses/" + resource.get().getUserId()).withSelfRel());
              
                return new ResponseEntity<>(model, HttpStatus.OK);
            }).exceptionally(ex ->{
              throw new RuntimeException(ex);
            });

  }



}
