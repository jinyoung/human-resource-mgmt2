package human.resource.mgmt.event;

import java.util.Date; 



import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ScheduleAddedEvent {

    private Long id;
    private String userId;
    private String title;
    private Date date;

}
