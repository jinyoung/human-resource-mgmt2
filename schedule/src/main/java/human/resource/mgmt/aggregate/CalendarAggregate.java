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
public class CalendarAggregate {

    @AggregateIdentifier
    private String userId;
    private List&lt;Schedule&gt; schedules;

    public CalendarAggregate(){}

    @CommandHandler
    public void handle(AddCommand command){

        ScheduleAddedEvent event = new ScheduleAddedEvent();
        BeanUtils.copyProperties(command, event);     


        apply(event);

    }

    @CommandHandler
    public void handle(CancelCommand command){

        ScheduleCanceledEvent event = new ScheduleCanceledEvent();
        BeanUtils.copyProperties(command, event);     


        apply(event);

    }








    @EventSourcingHandler
    public void on(ScheduleAddedEvent event) {
        BeanUtils.copyProperties(event, this);
    }


    @EventSourcingHandler
    public void on(ScheduleCanceledEvent event) {
        BeanUtils.copyProperties(event, this);
    }


}

