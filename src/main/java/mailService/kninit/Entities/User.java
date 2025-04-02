package mailService.kninit.Entities;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String firstName;
    private String lastName;
    private String password;
    private List<String> emails;
    private int academicYear;
    private String faculity;
    private String degree;
    private LocalDate dateOfBirth;
    private boolean agreement;
    private String studentIndex;
    private String token;
    private boolean verified;
    private long pointsGeneral;
    private List<Task> startedTasks;



}
