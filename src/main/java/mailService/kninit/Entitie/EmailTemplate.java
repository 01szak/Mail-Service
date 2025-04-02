package mailService.kninit.Entitie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection= "Email-templates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailTemplate {
    @Id
    private ObjectId id;
    private String templateName;
    private String description;
    private List<String> recipientGroups;
    private String templateHtmlBody;
    private String templateAlternateTextBody;
    private String subject;
}
