package human.resource.mgmt.api;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;

import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.responsetypes.ResponseTypes;
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
import java.util.Optional;

import human.resource.mgmt.aggregate.*;
import human.resource.mgmt.command.*;
import human.resource.mgmt.query.VacationDaysStatus;
import human.resource.mgmt.query.VacationDaysStatusSingleQuery;

//<<< Clean Arch / Inbound Adaptor
@RestController
public class VacationController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public VacationController(
        CommandGateway commandGateway,
        QueryGateway queryGateway
    ) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @RequestMapping(value = "/vacations", method = RequestMethod.POST)
    public CompletableFuture registerVacation(
        @RequestBody RegisterVacationCommand registerVacationCommand
    ) throws Exception {
        System.out.println("##### /vacation/registerVacation  called #####");

        VacationDaysStatusSingleQuery query = new VacationDaysStatusSingleQuery();
        query.setUserId(registerVacationCommand.getUserId());
        queryGateway.query(query, ResponseTypes.optionalInstanceOf(VacationDaysStatus.class))
            .get().ifPresent(leftDays -> {
                registerVacationCommand.setVacationDaysLeft(leftDays.getDaysLeft());
            });

        
        // send command
        return commandGateway
            .send(registerVacationCommand)
            .thenApply(id -> {
                VacationAggregate resource = new VacationAggregate();
                BeanUtils.copyProperties(registerVacationCommand, resource);

                resource.setId((String) id);

                EntityModel<VacationAggregate> model = EntityModel.of(resource);
                model.add(
                    Link.of("/vacations/" + resource.getId()).withSelfRel()
                );

                return new ResponseEntity<>(model, HttpStatus.OK);
            });
    }

    @RequestMapping(
        value = "/vacations/{id}/cancel",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public CompletableFuture cancel(
        @PathVariable("id") String id,
        @RequestBody CancelCommand cancelCommand
    ) throws Exception {
        System.out.println("##### /vacation/cancel  called #####");
        cancelCommand.setId(id);
        // send command
        return commandGateway.send(cancelCommand);
    }

    @RequestMapping(
        value = "/vacations/{id}/approve",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public CompletableFuture approve(
        @PathVariable("id") String id,
        @RequestBody ApproveCommand approveCommand
    ) throws Exception {
        System.out.println("##### /vacation/approve  called #####");
        approveCommand.setId(id);
        // send command
        return commandGateway.send(approveCommand);
    }

    @RequestMapping(
        value = "/vacations/{id}/confirmused",  //TODO
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8")
  public CompletableFuture confirmUsed(@PathVariable("id") String id, @RequestBody ConfirmUsedCommand confirmUsedCommand)
        throws Exception {
      System.out.println("##### /vacation/confirmUsed  called #####");
      confirmUsedCommand.setId(id);
      // send command
      return commandGateway.send(confirmUsedCommand);
  }


  @Autowired
  EventStore eventStore;

  @GetMapping(value="/vacations/{id}/events")
  public ResponseEntity getEvents(@PathVariable("id") String id){
      ArrayList resources = new ArrayList<VacationAggregate>(); 
      eventStore.readEvents(id).asStream().forEach(resources::add);

      CollectionModel<VacationAggregate> model = CollectionModel.of(resources);
                
      return new ResponseEntity<>(model, HttpStatus.OK);
  } 


}
//>>> Clean Arch / Inbound Adaptor
