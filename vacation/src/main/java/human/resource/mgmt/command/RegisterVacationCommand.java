package human.resource.mgmt.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import java.util.Date; 

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class RegisterVacationCommand {


//    private String id;  // Please comment here if you want user to enter the id directly
    private Date startDate;
    private Date endDate;
    private String reason;
    private String userId;
    private Integer days;
    private int vacationDaysLeft;

}
