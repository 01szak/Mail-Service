package mailService.kninit.Entitie;

import lombok.*;
import mailService.kninit.Enum.Emails;
import mailService.kninit.Enum.ScheduleStatus;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
@Document("Schedules")
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    private ObjectId id;

    @Field("date")
    private LocalDateTime date;

    @Field("from")
    private Emails from;

    @Field("receiving_group")
    private List<String> receivingGroups;

    @Field("template_id")
    private ObjectId templateId;

    @Field("dynamic_variables")
    private String[] dynamicVariables;

    @Field("status")
    private ScheduleStatus scheduleStatus;
}
