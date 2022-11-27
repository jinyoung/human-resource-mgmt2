package human.resource.mgmt.event;




import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class VacationDaysUsedEvent {

    private Long id;
    private String userId;
    private Integer dayCount;

}
