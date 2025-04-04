package mailService.kninit.Service;

import lombok.AllArgsConstructor;
import mailService.kninit.Entitie.EmailTemplate;
import mailService.kninit.Entitie.Request;
import mailService.kninit.Repository.EmailTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private EmailTemplateRepository emailTemplateRepository;

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
    @Transactional
    public void saveTemplate(Request.CreateTemplateRequest request) {
        EmailTemplate.EmailTemplateBuilder builder = EmailTemplate.builder();

        Optional.ofNullable(request.templateName()).ifPresent(builder::templateName);
        Optional.ofNullable(request.description()).ifPresent(builder::description);
        Optional.ofNullable(request.recipientGroups()).ifPresent(builder::recipientGroups);
        Optional.ofNullable(request.templateHtmlBody()).ifPresent(builder::templateHtmlBody);
        Optional.ofNullable(request.templateAlternateTextBody()).ifPresent(builder::templateAlternateTextBody);
        Optional.ofNullable(request.subject()).ifPresent(builder::subject);

        emailTemplateRepository.save(builder.build());
    }


}
