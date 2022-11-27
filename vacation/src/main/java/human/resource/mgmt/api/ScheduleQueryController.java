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
public class ScheduleQueryController {

  private final QueryGateway queryGateway;

  public ScheduleQueryController(QueryGateway queryGateway) {
      this.queryGateway = queryGateway;
  }
  

  @GetMapping("/schedules")
  public CompletableFuture findAll() {
      return queryGateway.query(new ScheduleQuery(), ResponseTypes.multipleInstancesOf(Schedule.class))
              .thenApply(resources -> {
                CollectionModel<Schedule> model = CollectionModel.of(resources);
                
                return new ResponseEntity<>(model, HttpStatus.OK);
            });

  }

  @GetMapping("/orders/{id}")
  public CompletableFuture findById(@PathVariable("id") String id) {
    ScheduleSingleQuery query = new ScheduleSingleQuery();

    //TODO
    query.setUserId(id);

      return queryGateway.query(query, ResponseTypes.optionalInstanceOf(Schedule.class))
              .thenApply(resource -> {
                if(!resource.isPresent()){
                  return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }

                EntityModel<Schedule> model = EntityModel.of(resource.get());
                model                                             //TODO
                      .add(Link.of("/schedules/" + resource.get().getUserId()).withSelfRel());
              
                return new ResponseEntity<>(model, HttpStatus.OK);
            }).exceptionally(ex ->{
              throw new RuntimeException(ex);
            });

  }



}

