package human.resource.mgmt.query;

import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.List;
import java.util.Date;
import lombok.Data;


@Entity
@Table(name="CalendarReadModel_table")
@Data
@Relation(collectionRelation = "calendarReadModels")
public class CalendarReadModel {

        @Id
        //@GeneratedValue(strategy=GenerationType.AUTO)
        private String userId;
        private String yearAndMonth;
        private String scheduleData;


}
