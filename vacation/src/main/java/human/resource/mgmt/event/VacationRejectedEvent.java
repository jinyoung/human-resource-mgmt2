package human.resource.mgmt.event;

import java.util.Date; 



import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class VacationRejectedEvent {

    private Long id;
    private Date startDate;
    private Date endDate;
    private String reason;

}
