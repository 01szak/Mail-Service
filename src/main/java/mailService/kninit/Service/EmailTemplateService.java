package mailService.kninit.Service;

import java.util.Map;

public interface EmailTemplateService {
    String generateEmail(String templateName, Map<String,Object> variables);

}
