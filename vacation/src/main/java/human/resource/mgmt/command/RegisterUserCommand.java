package human.resource.mgmt.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;



import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class RegisterUserCommand {


        private String userId;  // Please comment here if you want user to enter the id directly
        private Integer dayCount;

}
