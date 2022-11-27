package human.resource.mgmt.query;

import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.util.List;
import java.util.Date;
import lombok.Data;


@Entity
@Table(name="Schedule_table")
@Data
@Relation(collectionRelation = "schedules")
public class Schedule {

        @Id
        //@GeneratedValue(strategy=GenerationType.AUTO)
        private String userId;
        private String dateId;


}
