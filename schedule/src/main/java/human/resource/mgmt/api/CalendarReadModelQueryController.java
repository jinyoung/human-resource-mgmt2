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
public class CalendarReadModelQueryController {

  private final QueryGateway queryGateway;

  public CalendarReadModelQueryController(QueryGateway queryGateway) {
      this.queryGateway = queryGateway;
  }
  

  @GetMapping("/calendarReadModels")
  public CompletableFuture findAll() {
      return queryGateway.query(new CalendarReadModelQuery(), ResponseTypes.multipleInstancesOf(CalendarReadModel.class))
              .thenApply(resources -> {
                CollectionModel<CalendarReadModel> model = CollectionModel.of(resources);
                
                return new ResponseEntity<>(model, HttpStatus.OK);
            });

  }

  @GetMapping("/calendarReadModels/{id}")
  public CompletableFuture findById(@PathVariable("id") String id) {
    CalendarReadModelSingleQuery query = new CalendarReadModelSingleQuery();
    query.setUserId(id);

      return queryGateway.query(query, ResponseTypes.optionalInstanceOf(CalendarReadModel.class))
              .thenApply(resource -> {
                if(!resource.isPresent()){
                  return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }

                EntityModel<CalendarReadModel> model = EntityModel.of(resource.get());
                model
                      .add(Link.of("/calendarReadModels/" + resource.get().getUserId()).withSelfRel());
              
                return new ResponseEntity<>(model, HttpStatus.OK);
            }).exceptionally(ex ->{
              throw new RuntimeException(ex);
            });

  }



}
