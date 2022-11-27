package human.resource.mgmt.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import static org.axonframework.modelling.command.AggregateLifecycle.*;
import org.axonframework.spring.stereotype.Aggregate;

import org.springframework.beans.BeanUtils;
import java.util.List;

import lombok.Data;
import lombok.ToString;




import human.resource.mgmt.command.*;
import human.resource.mgmt.event.*;

@Aggregate
@Data
@ToString
public class EmployeeAggregate {

    @AggregateIdentifier
    private String userId;
    private String name;
    private String email;

    public EmployeeAggregate(){}

    @CommandHandler
    public EmployeeAggregate(JoinCommand command){

        EmployeeJoinedEvent event = new EmployeeJoinedEvent();
        BeanUtils.copyProperties(command, event);     

                //Please uncomment here and implement the createUUID method.
        //event.setId(createUUID());
        
        apply(event);

    }

    @CommandHandler
    public void handle(ResignCommand command){

        EmployeeResignedEvent event = new EmployeeResignedEvent();
        BeanUtils.copyProperties(command, event);     


        apply(event);

    }








    @EventSourcingHandler
    public void on(EmployeeJoinedEvent event) {
        BeanUtils.copyProperties(event, this);
        
    }


    @EventSourcingHandler
    public void on(EmployeeResignedEvent event) {
        BeanUtils.copyProperties(event, this);
        
    }


}

