package human.resource.mgmt.query;

import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.List;
import java.util.Date;
import lombok.Data;


@Entity
@Table(name="VacationStatus_table")
@Data
@Relation(collectionRelation = "vacationStatuses")
public class VacationStatus {

        @Id
        //@GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        private Date startDate;
        private Date endDate;
        private String reason;
        private String status;


}
