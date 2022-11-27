package human.resource.mgmt.api;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;

import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.axonframework.eventsourcing.eventstore.EventStore;

import org.springframework.beans.BeanUtils;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;

import java.util.concurrent.CompletableFuture;
import java.util.ArrayList;


import human.resource.mgmt.aggregate.*;
import human.resource.mgmt.command.*;

@RestController
public class EmployeeController {

  private final CommandGateway commandGateway;
  private final QueryGateway queryGateway;

  public EmployeeController(CommandGateway commandGateway, QueryGateway queryGateway) {
      this.commandGateway = commandGateway;
      this.queryGateway = queryGateway;
  }

  @RequestMapping(value = "/employees",
        method = RequestMethod.POST
      )
  public CompletableFuture join(@RequestBody JoinCommand joinCommand)
        throws Exception {
      System.out.println("##### /employee/join  called #####");

      // send command
      return commandGateway.send(joinCommand)            
            .thenApply(
            id -> {
                  EmployeeAggregate resource = new EmployeeAggregate();
                  BeanUtils.copyProperties(joinCommand, resource);

                  resource.setUserId((String)id);
                  
                  EntityModel<EmployeeAggregate> model = EntityModel.of(resource);
                  model
                        .add(Link.of("/employees/" + resource.getUserId()).withSelfRel());

                  return new ResponseEntity<>(model, HttpStatus.OK);
            }
      );

  }



  @RequestMapping(value = "/employees/{id}/resign",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8")
  public CompletableFuture resign(@PathVariable("id") String id, @RequestBody ResignCommand resignCommand)
        throws Exception {
      System.out.println("##### /employee/resign  called #####");
      resignCommand.setUserId(id);
      // send command
      return commandGateway.send(resignCommand);
  }


  @Autowired
  EventStore eventStore;

  @GetMapping(value="/employees/{id}/events")
  public ResponseEntity getEvents(@PathVariable("id") String id){
      ArrayList resources = new ArrayList<EmployeeAggregate>(); 
      eventStore.readEvents(id).asStream().forEach(resources::add);

      CollectionModel<VacationAggregate> model = CollectionModel.of(resources);
                
      return new ResponseEntity<>(model, HttpStatus.OK);
  } 


}
