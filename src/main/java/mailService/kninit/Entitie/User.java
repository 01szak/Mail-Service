package mailService.kninit.Entitie;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Admin.class, name = "admin"),
        @JsonSubTypes.Type(value = Guest.class, name = "guest")
})

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
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

    @Field("token")
    private String token;

    @Field("verified")
    private boolean verified;
}
