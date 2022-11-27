package human.resource.mgmt.api;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.beans.BeanUtils;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;

import java.util.concurrent.CompletableFuture;


import human.resource.mgmt.aggregate.*;
import human.resource.mgmt.command.*;

@RestController
public class VacationController {

  private final CommandGateway commandGateway;
  private final QueryGateway queryGateway;

  public VacationController(CommandGateway commandGateway, QueryGateway queryGateway) {
      this.commandGateway = commandGateway;
      this.queryGateway = queryGateway;
  }

  @RequestMapping(value = "/vacations",
        method = RequestMethod.POST
      )
  public CompletableFuture registerVacation(@RequestBody RegisterVacationCommand registerVacationCommand)
        throws Exception {
      System.out.println("##### /vacation/registerVacation  called #####");

      // send command
      return commandGateway.send(registerVacationCommand)            
            .thenApply(
            id -> {
                  VacationAggregate resource = new VacationAggregate();
                  BeanUtils.copyProperties(registerVacationCommand, resource);

                  resource.setId(id);
                  
                  EntityModel<VacationAggregate> model = EntityModel.of(resource);
                  model
                        .add(Link.of("/vacations/" + resource.getId()).withSelfRel());

                  return new ResponseEntity<>(model, HttpStatus.OK);
            }
      );

  }



  @RequestMapping(value = "/vacations/cancel",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8")
  public CompletableFuture cancel(@RequestBody CancelCommand cancelCommand)
        throws Exception {
      System.out.println("##### /vacation/cancel  called #####");

      // send command
      return commandGateway.send(cancelCommand);
  }


  @RequestMapping(value = "/vacations/approve",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8")
  public CompletableFuture approve(@RequestBody ApproveCommand approveCommand)
        throws Exception {
      System.out.println("##### /vacation/approve  called #####");

      // send command
      return commandGateway.send(approveCommand);
  }


  @RequestMapping(value = "/vacations/confirmused",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8")
  public CompletableFuture confirmUsed(@RequestBody ConfirmUsedCommand confirmUsedCommand)
        throws Exception {
      System.out.println("##### /vacation/confirmUsed  called #####");

      // send command
      return commandGateway.send(confirmUsedCommand);
  }
}
