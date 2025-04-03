package mailService.kninit.Entitie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private ObjectId id;

    @Field("created_at")
    private LocalDate createdAt;

    @Field("updated_at")
    private LocalDate updatedAt;

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    @Field("password")
    private String password;

    @Field("emails")
    private List<String> emails;

    @Field("academic_year")
    private int academicYear;

    @Field("faculity")
    private String faculity;

    @Field("degree")
    private String degree;

    @Field("date_of_birth")
    private LocalDate dateOfBirth;

    @Field("agreement")
    private boolean agreement;

    @Field("student_index")
    private String studentIndex;

    @Field("token")
    private String token;

    @Field("verified")
    private boolean verified;

    @Field("points_general")
    private long pointsGeneral;

    @Field("started_tasks")
    private List<Task> startedTasks;



}
