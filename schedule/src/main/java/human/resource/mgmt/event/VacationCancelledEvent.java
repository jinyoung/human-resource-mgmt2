package human.resource.mgmt.event;

import java.util.Date; 



import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class VacationCancelledEvent {

    private String id;
    private Date startDate;
    private Date endDate;
    private String reason;
    private String userId;

}
