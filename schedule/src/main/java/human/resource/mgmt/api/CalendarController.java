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
public class CalendarController {

  private final CommandGateway commandGateway;
  private final QueryGateway queryGateway;

  public CalendarController(CommandGateway commandGateway, QueryGateway queryGateway) {
      this.commandGateway = commandGateway;
      this.queryGateway = queryGateway;
  }


  @RequestMapping(value = "/add",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8")
  public CompletableFuture add(@RequestBody AddCommand addCommand)
        throws Exception {
      System.out.println("##### /calendar/add  called #####");

      // send command
      return commandGateway.send(addCommand);
  }


  @RequestMapping(value = "/cancel",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8")
  public CompletableFuture cancel(@RequestBody CancelCommand cancelCommand)
        throws Exception {
      System.out.println("##### /calendar/cancel  called #####");

      // send command
      return commandGateway.send(cancelCommand);
  }
}
