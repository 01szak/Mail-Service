package mailService.kninit.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import mailService.kninit.Entitie.Admin;
import mailService.kninit.Entitie.EmailTemplate;
import mailService.kninit.Entitie.Request;
import mailService.kninit.Entitie.User;
import mailService.kninit.Enum.Emails;
import mailService.kninit.Repository.AdminRepository;
import mailService.kninit.Repository.GuestRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender javaMailSender;

    private final GuestRepository guestRepository;

    private final AdminRepository adminRepository;

    private final EmailTemplateServiceImpl emailTemplateService;


    public void testSendMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("test@example.com");
        message.setSubject("Test Mail");
        message.setText("Hello from Spring Boot!");
        javaMailSender.send(message);
        System.out.println("Mail Sent!");
    }


    @Async
    @Override
    public void sendEmail(Emails from, EmailTemplate emailTemplate, User user) {

            try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                if (emailTemplate.getTemplateHtmlBody().isEmpty()) {
                    helper.setText(emailTemplate.getTemplateAlternateTextBody(),false);

                } else {
                    helper.setText(emailTemplate.getTemplateHtmlBody(),true);
                }

                helper.setFrom(from.getEmail());
                helper.setTo(user.getEmails().get(0));
                helper.setSubject(emailTemplate.getSubject());
                javaMailSender.send(mimeMessage);

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }catch (NullPointerException e ) {
                System.out.printf("mail was not send to User: %s %s\n",user.getFirstName(),user.getLastName() );
            }
    }
    public void sendVerificationEmail(Request.SendVerificationEmailRequest request) {
        Map<String,Object> variables = Map.of(
           "Value_1", request.verificationLink()
        );

        String templateName = request.personToVerify() instanceof Admin ? "admin_verification": "icc_account_verification";
        String htmlBody = emailTemplateService.generateEmail(emailTemplateService.findByTemplateName(templateName),variables).htmlBody();
        String subject = emailTemplateService.generateEmail(emailTemplateService.findByTemplateName(templateName),variables).subject();
        EmailTemplate emailTemplate = EmailTemplate.builder()
                .templateName(templateName)
                .templateHtmlBody(htmlBody)
                .subject(subject)
                .build();
        sendEmail(request.from(),emailTemplate,request.personToVerify());
    }
    public void sendEmailToAll(List<String> receivingGroups, Request.SendEmailToAllRequest request) {
        Map<String,Object> variables = new HashMap<>();
//        EmailTemplate.EmailTemplateBuilder emailTemplate = EmailTemplate.builder();

        List<User> to = chooseUsersToReceiveEmail(receivingGroups);

        if (request.newTemplate() != null ) {
            emailTemplateService.saveTemplate(request.newTemplate());
        }

        for( User u : to ) {
        EmailTemplate emailTemplate = new EmailTemplate();

        if (request.newTemplate() == null && request.templateName() != null) {
            emailTemplate = emailTemplateService.findByTemplateName(request.templateName());

            variables = setVariables(request.dynamicVariables(),emailTemplate.getTemplateHtmlBody(),emailTemplate.getSubject(),u);

            emailTemplate.setSubject(emailTemplateService.generateEmail(emailTemplate, variables).subject());
            emailTemplate.setTemplateHtmlBody(emailTemplateService.generateEmail(emailTemplate, variables).htmlBody());

        } else if (request.newTemplate() != null && request.templateName() == null) {

            emailTemplate = emailTemplateService.findByTemplateName(request.newTemplate().templateName());

            variables = setVariables(request.dynamicVariables(),emailTemplate.getTemplateHtmlBody(),emailTemplate.getSubject(),u);
            emailTemplate.setSubject(emailTemplateService.generateEmail(emailTemplate, variables).subject());
            emailTemplate.setTemplateHtmlBody(emailTemplateService.generateEmail(emailTemplate, variables).htmlBody());

        } else if (request.newTemplate() != null && request.templateName() != null) {
            throw new IllegalStateException("Two templates have been selected");
        } else {
            throw new IllegalStateException("Chose or create template");
        }
            sendEmail(request.from(), emailTemplate,u);
        };
    }
    private Map<String, Object> setVariables (String[] dynamicVariables, String templateHtmlBody,String subject,User u ) {
        Map<String,Object> variables = new HashMap<>();

        for (int i = 1; i < dynamicVariables.length + 1; i++) {
            variables.put("Value_" + String.valueOf(i),dynamicVariables[i - 1]);
        }
        if (templateHtmlBody.contains("{{firstname}}") || subject.contains("{{firstname}}")) {
            variables.put("firstname", u.getFirstName());
        }
        if(templateHtmlBody.contains("{{lastname}}") || subject.contains("{{lastname}}")) {
            variables.put("lastname", u.getLastName());
        }
        if (templateHtmlBody.contains("{{email}}") || subject.contains("{{email}}")) {
            variables.put("email", u.getEmails().get(0));
        }
        return variables;
    }
    private List<User> chooseUsersToReceiveEmail (List<String> receivingGroups) {
        List<User> to = new ArrayList<>();
        if (receivingGroups.isEmpty()) {
            to.addAll(guestRepository.findAll());
            to.addAll(adminRepository.findAll());
        } else if (receivingGroups.size() == 1 && receivingGroups.contains("admin")) {
            to.addAll(adminRepository.findAll());
        }else if (receivingGroups.size() == 1 && receivingGroups.contains("guest")){
            to.addAll(guestRepository.findAll());
        }
        return to;
    }
//    public void sendPlainTextEmail(Emails from,String subject,String text) {
//        sendEmail(from,subject,text);
//    }
}
