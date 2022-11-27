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
public class VacationDaysLeftAggregate {

    @AggregateIdentifier
    private String userId;
    private Integer dayCount;

    public VacationDaysLeftAggregate(){}

    @CommandHandler
    public void handle(AddCommand command){

        VacationDaysAddedEvent event = new VacationDaysAddedEvent();
        BeanUtils.copyProperties(command, event);     


        apply(event);

    }

    @CommandHandler
    public void handle(UseCommand command){

        VacationDaysUsedEvent event = new VacationDaysUsedEvent();
        BeanUtils.copyProperties(command, event);     


        apply(event);

    }








    @EventSourcingHandler
    public void on(VacationDaysAddedEvent event) {
        BeanUtils.copyProperties(event, this);
    }


    @EventSourcingHandler
    public void on(VacationDaysUsedEvent event) {
        BeanUtils.copyProperties(event, this);
    }


}

