package mailService.kninit.Entitie;

import mailService.kninit.Enum.Emails;
import org.springframework.lang.Nullable;

import java.util.List;

public class Request {
    public record SendVerificationEmailRequest(Emails from, String subject, String verificationLink,User personToVerify ) {}
    public record SendPlainTextEmailRequest(Emails from, String subject, String body) {}
    public record SendEmailToAllRequest(@Nullable CreateTemplateRequest newTemplate, Emails from, @Nullable String templateName, String ... dynamicVariables) {}
    public record CreateTemplateRequest(String templateName, String description, List<String> recipientGroups, String templateHtmlBody, String templateAlternateTextBody, String subject ) {}
}
