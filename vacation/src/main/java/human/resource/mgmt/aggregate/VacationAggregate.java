package human.resource.mgmt.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.command.AggregateIdentifier;

import static org.axonframework.modelling.command.AggregateLifecycle.*;
import org.axonframework.spring.stereotype.Aggregate;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import lombok.Data;
import lombok.ToString;

import java.util.Date; 



import human.resource.mgmt.command.*;
import human.resource.mgmt.event.*;
import human.resource.mgmt.query.VacationDaysStatusRepository;
import human.resource.mgmt.query.VacationDaysStatusSingleQuery;

@Aggregate
@Data
@ToString
public class VacationAggregate {

    @AggregateIdentifier
    private String id;
    private Date startDate;
    private Date endDate;
    private int days;
    private String reason;
    private String userId;
    private boolean used;

    public VacationAggregate(){}

    @Autowired VacationDaysStatusRepository vacationDaysStatusRepository;

    @CommandHandler
    public VacationAggregate(RegisterVacationCommand command){

        VacationDaysStatusSingleQuery daysLeftQuery = new VacationDaysStatusSingleQuery();
        daysLeftQuery.setUserId(command.getUserId());

        if(command.getVacationDaysLeft() < command.getDays())
            throw new IllegalStateException("No vacation days are left to " + command.getUserId());        

        VacationRegisteredEvent event = new VacationRegisteredEvent();
        BeanUtils.copyProperties(command, event);     

        //Please uncomment here and implement the createUUID method.
        event.setId(createUUID());
        
        apply(event);

    }

    //TODO
    private String createUUID() {
        return java.util.UUID.randomUUID().toString();
    }

    @CommandHandler
    public void handle(CancelCommand command){

        VacationCancelledEvent event = new VacationCancelledEvent();
        BeanUtils.copyProperties(command, event);     


        apply(event);

    }

    @CommandHandler
    public void handle(ApproveCommand command){

        if(command.getApprove()==null || command.getApprove()==true){
            VacationApprovedEvent event = new VacationApprovedEvent();
            BeanUtils.copyProperties(this, event);     
    
            apply(event);
    
        }else{
            VacationRejectedEvent event = new VacationRejectedEvent();
            BeanUtils.copyProperties(this, event);     
        
            apply(event);
        }
    }

    @CommandHandler
    public void handle(ConfirmUsedCommand command){

        if(isUsed()) throw new IllegalStateException("Already Used");

        VacationUsedEvent event = new VacationUsedEvent();
        BeanUtils.copyProperties(this, event);     

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

