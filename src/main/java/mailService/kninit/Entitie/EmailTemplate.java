package mailService.kninit.Entitie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection= "Email_templates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailTemplate {
    @Id
    private ObjectId id;

    @Field("template_name")
    private String templateName;

    @Field("description")
    private String description;

    @Field("recipient_groups")
    private List<String> recipientGroups;

    @Field("template_html_body")
    private String templateHtmlBody;

    @Field("template_alternate_text_body")
    private String templateAlternateTextBody;

    @Field("subject")
    private String subject;
}
