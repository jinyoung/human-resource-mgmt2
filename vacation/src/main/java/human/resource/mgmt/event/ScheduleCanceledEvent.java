package human.resource.mgmt.event;

import java.util.Date; 



import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ScheduleCanceledEvent {

    private Long id;
    private String title;
    private Date date;

}
