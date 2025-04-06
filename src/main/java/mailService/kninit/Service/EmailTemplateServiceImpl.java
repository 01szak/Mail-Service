package mailService.kninit.Service;

import lombok.AllArgsConstructor;
import mailService.kninit.Entitie.EmailTemplate;
import mailService.kninit.Entitie.Request;
import mailService.kninit.Repository.EmailTemplateRepository;
import org.bson.types.ObjectId;
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
    public record GeneratedEmail(String subject, String htmlBody){}
    @Override
    public GeneratedEmail generateEmail(EmailTemplate rawTemplate, Map<String, Object> variables) throws NullPointerException{
            if (rawTemplate == null) {
                throw new IllegalStateException("Template not found or incorrect template name");
            }

            String thymeleafTemplate = convertTemplate(rawTemplate.getTemplateHtmlBody());
            String thymeleafSubject = convertTemplate(rawTemplate.getSubject());

            Context context = new Context();
            context.setVariables(variables);

            String generatedBody = templateEngine.process(thymeleafTemplate, context);
            String generatedSubject = templateEngine.process(thymeleafSubject, context);

             return new GeneratedEmail(generatedSubject,generatedBody);
    }
    private String convertTemplate(String rawTemplate) {
        return rawTemplate.replaceAll("\\{\\{\\s*\\.?(\\w+)\\s*}}", "\\[\\[\\${$1}\\]\\]");
    }

    @Override
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

    @Override
    public EmailTemplate findById(ObjectId id) {
        return emailTemplateRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void deleteById(ObjectId id) {
        emailTemplateRepository.deleteById(id);
    }

    @Override
    public EmailTemplate findByTemplateName(String templateName) {
        return emailTemplateRepository.findByTemplateName(templateName);
    }
}
