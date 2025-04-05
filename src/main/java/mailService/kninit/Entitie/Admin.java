package mailService.kninit.Entitie;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@JsonTypeName("admin")

@Document(collection = "Admins")

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends User {

    @Field("discord_username")
    private String discordUserName;

    @Field("admin_permissions")
    private List<String> adminPermissions;

}
