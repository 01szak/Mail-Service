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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
           "VerificationLink", request.verificationLink()
        );

        String templateName = request.personToVerify() instanceof Admin ? "admin_verification": "icc_account_verification";
        String htmlBody = emailTemplateService.generateEmail(templateName,variables).htmlBody();
        String subject = emailTemplateService.generateEmail(templateName,variables).subject();
        EmailTemplate emailTemplate = EmailTemplate.builder()
                .templateName(templateName)
                .templateHtmlBody(htmlBody)
                .subject(subject)
                .build();
        sendEmail(request.from(),emailTemplate,request.personToVerify());
    }
    public void sendEmailToAll(List<String> receivingGroups, Request.SendEmailToAllRequest request) {
        Map<String,Object> variables = new HashMap<>();
        List<User> to = new ArrayList<>();
        EmailTemplate.EmailTemplateBuilder emailTemplate = EmailTemplate.builder();

        for (int i = 1; i < request.dynamicVariables().length + 1; i++) {
            variables.put("Value_" + String.valueOf(i),request.dynamicVariables()[i - 1]);
        }

        if (request.newTemplate() == null && request.templateName() != null) {

            emailTemplate.templateName(request.templateName());
            emailTemplate.subject(emailTemplateService.generateEmail(request.templateName(), variables).subject());
            emailTemplate.templateHtmlBody(emailTemplateService.generateEmail(request.templateName(), variables).htmlBody());
            emailTemplate.description(emailTemplateService.findByTemplateName(request.templateName()).getDescription());
            emailTemplate.templateAlternateTextBody(emailTemplateService.findByTemplateName(request.templateName()).getTemplateAlternateTextBody());

        } else if (request.newTemplate() != null && request.templateName() == null) {
            emailTemplateService.saveTemplate(request.newTemplate());

            emailTemplate.templateName(request.newTemplate().templateName());
            emailTemplate.subject(emailTemplateService.generateEmail(request.newTemplate().templateName(), variables).subject());
            emailTemplate.templateHtmlBody(emailTemplateService.generateEmail(request.newTemplate().templateName(), variables).htmlBody());
            emailTemplate.description(request.newTemplate().description());
            emailTemplate.templateAlternateTextBody(request.newTemplate().templateAlternateTextBody());

        } else if (request.newTemplate() != null && request.templateName() != null) {
            throw new IllegalStateException("Two templates have been selected");
        } else {
            throw new IllegalStateException("Chose or create template");
        }

        if (receivingGroups.isEmpty()) {
            to.addAll(guestRepository.findAll());
            to.addAll(adminRepository.findAll());
        } else if (receivingGroups.size() == 1 && receivingGroups.contains("admin")) {
            to.addAll(adminRepository.findAll());
        }else if (receivingGroups.size() == 1 && receivingGroups.contains("guest")){
            to.addAll(guestRepository.findAll());
        }

        to.forEach(u -> {
            sendEmail(request.from(), emailTemplate.build(),u);
        });
    }

//    public void sendPlainTextEmail(Emails from,String subject,String text) {
//        sendEmail(from,subject,text);
//    }
}
