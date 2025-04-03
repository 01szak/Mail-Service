package mailService.kninit.Service;

import lombok.AllArgsConstructor;
import mailService.kninit.Entitie.EmailTemplate;
import mailService.kninit.Repository.EmailTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@AllArgsConstructor
public class EmailTemplateServiceImpl implements EmailTemplateService {

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private TemplateEngine templateEngine;
    @Override
    public String generateEmail(String templateName, Map<String, Object> variables) throws NullPointerException{

        EmailTemplate rawTemplate = emailTemplateRepository.findByTemplateName(templateName);


        String thymeleafTemplate = convertTemplate(rawTemplate.getTemplateHtmlBody());

        Context context = new Context();
        context.setVariables(variables);

        return templateEngine.process(thymeleafTemplate, context);
    }
    private String convertTemplate(String rawTemplate) {
        return rawTemplate.replaceAll("\\{\\{\\s*\\.([a-zA-Z0-9_]+)\\s*}}", "\\[\\[\\${$1}\\]\\]");

    }

}
