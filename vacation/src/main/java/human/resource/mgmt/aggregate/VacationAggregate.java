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

import java.util.Date; 



import human.resource.mgmt.command.*;
import human.resource.mgmt.event.*;

@Aggregate
@Data
@ToString
public class VacationAggregate {

    @AggregateIdentifier
    private String id;
    private Date startDate;
    private Date endDate;
    private String reason;
    private String userId;

    public VacationAggregate(){}

    @CommandHandler
    public VacationAggregate(RegisterVacationCommand command){

        VacationRegisteredEvent event = new VacationRegisteredEvent();
        BeanUtils.copyProperties(command, event);     

                //Please uncomment here and implement the createUUID method.
        //event.setId(createUUID());
        
        apply(event);

    }

    @CommandHandler
    public void handle(CancelCommand command){

        VacationCancelledEvent event = new VacationCancelledEvent();
        BeanUtils.copyProperties(command, event);     


        apply(event);

    }

    @CommandHandler
    public void handle(ApproveCommand command){

        VacationApprovedEvent event = new VacationApprovedEvent();
        BeanUtils.copyProperties(command, event);     


        apply(event);

        VacationRejectedEvent event = new VacationRejectedEvent();
        BeanUtils.copyProperties(command, event);     


        apply(event);

    }

    @CommandHandler
    public void handle(ConfirmUsedCommand command){

        VacationUsedEvent event = new VacationUsedEvent();
        BeanUtils.copyProperties(command, event);     


        apply(event);

    }








    @EventSourcingHandler
    public void on(VacationRegisteredEvent event) {
        BeanUtils.copyProperties(event, this);
    }


    @EventSourcingHandler
    public void on(VacationCancelledEvent event) {
        BeanUtils.copyProperties(event, this);
    }


    @EventSourcingHandler
    public void on(VacationApprovedEvent event) {
        BeanUtils.copyProperties(event, this);
    }


    @EventSourcingHandler
    public void on(VacationRejectedEvent event) {
        BeanUtils.copyProperties(event, this);
    }


    @EventSourcingHandler
    public void on(VacationUsedEvent event) {
        BeanUtils.copyProperties(event, this);
    }


}

