package human.resource.mgmt.event;




import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class EmployeeJoinedEvent {

    private Long id;
    private String userId;
    private String name;
    private String email;

}
