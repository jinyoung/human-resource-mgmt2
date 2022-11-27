package human.resource.mgmt.query;

import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.List;
import java.util.Date;
import lombok.Data;


@Entity
@Table(name="VacationDaysStatus_table")
@Data
@Relation(collectionRelation = "vacationDaysStatuses")
public class VacationDaysStatus {

        @Id
        //@GeneratedValue(strategy=GenerationType.AUTO)
        private String userId;
        private Integer daysLeft;


}
