package mailService.kninit.Entitie;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;
@JsonTypeName("guest")

@Document(collection = "Users")

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Guest extends User  {
    @Field("academic_year")
    private int academicYear;

    @Field("faculity")
    private String faculity;

    @Field("degree")
    private String degree;

    @Field("date_of_birth")
    private LocalDate dateOfBirth;

    @Field("student_index")
    private String studentIndex;

    @Field("points_general")
    private long pointsGeneral;

    @Field("started_tasks")
    private List<String> startedTasks;

}
