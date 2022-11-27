package human.resource.mgmt.event;

import lombok.Data;
import lombok.ToString;

import java.util.Date; 




@Data
@ToString
public class VacationCancelledEvent {

    private String id;
    private Date startDate;
    private Date endDate;
    private String reason;
    private String userId;

}
