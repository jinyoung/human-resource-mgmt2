package human.resource.mgmt.policy;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.eventhandling.DisallowReplay;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.axonframework.commandhandling.gateway.CommandGateway;

import human.resource.mgmt.command.*;
import human.resource.mgmt.event.*;
import human.resource.mgmt.aggregate.*;


@Service
@ProcessingGroup("vacation")
public class PolicyHandler{

    @Autowired
    CommandGateway commandGateway;

    @EventHandler
    //@DisallowReplay
    public void wheneverVacationRegistered_Add(VacationRegisteredEvent vacationRegistered){
        System.out.println(vacationRegistered.toString());

        AddCommand command = new AddCommand();
        commandGateway.send(command);
    }
    @EventHandler
    //@DisallowReplay
    public void wheneverVacationUsed_Use(VacationUsedEvent vacationUsed){
        System.out.println(vacationUsed.toString());

        UseCommand command = new UseCommand();
        commandGateway.send(command);
    }

}
