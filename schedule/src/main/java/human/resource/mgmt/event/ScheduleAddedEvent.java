package human.resource.mgmt.event;

import lombok.Data;
import lombok.ToString;

import java.util.Date; 




@Data
@ToString
public class ScheduleAddedEvent {

    private Long id;
    private String userId;
    private String title;
    private Date date;

}
