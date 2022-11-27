package human.resource.mgmt.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;



import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class UseCommand {


    @TargetAggregateIdentifier
    private String userId;
    private Integer dayCount;

}
