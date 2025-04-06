package mailService.kninit.Service;

import mailService.kninit.Entitie.EmailTemplate;
import mailService.kninit.Entitie.Request;
import org.bson.types.ObjectId;

import java.util.Map;

public interface EmailTemplateService {
    EmailTemplateServiceImpl.GeneratedEmail generateEmail(EmailTemplate rawTemplate, Map<String, Object> variables);

    EmailTemplate findByTemplateName(String templateName);

    void saveTemplate(Request.CreateTemplateRequest request);

    EmailTemplate findById(ObjectId id);
}
