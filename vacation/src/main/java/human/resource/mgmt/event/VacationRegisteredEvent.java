package human.resource.mgmt.event;

import java.util.Date; 



import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class VacationRegisteredEvent {

    private Long id;
    private Date startDate;
    private Date endDate;
    private String reason;
    private String userId;

}
