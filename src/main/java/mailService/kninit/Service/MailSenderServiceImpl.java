package mailService.kninit.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import mailService.kninit.Entitie.Admin;
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
    public void sendEmail(Emails from, String subject, String body, User user) {
        boolean isHtml;

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        if (body.startsWith("<!DOCTYPE html>")) {

            isHtml = true;
        } else {

            isHtml = false;
        }
            try {

                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setFrom(from.getEmail());
                helper.setTo(user.getEmails().get(0));
                helper.setSubject(subject);
                helper.setText(body,isHtml);
                javaMailSender.send(mimeMessage);

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }catch (NullPointerException e ) {
                System.out.printf("mail was not send to User: %s %s",user.getFirstName(),user.getLastName() );
            }
    }
    public void sendVerificationEmail(Request.SendVerificationEmailRequest request) {

        Map<String,Object> variables = Map.of(
           "VerificationLink", request.verificationLink()
        );


        String templateName = request.personToVerify() instanceof Admin ? "admin_verification": "icc_account_verification";
        String htmlBody = emailTemplateService.generateEmail(templateName,variables);

        sendEmail(request.from(),request.subject(),htmlBody,request.personToVerify());
    }
    public void sendEmailToAll(String receivingGroup, Request.SendEmailToAllRequest request) {
        Map<String,Object> variables = new HashMap<>();
        List<User> to = new ArrayList<>();

//        if(request.newTemplate() != null) {
//            String htmlBody = request.newTemplate().templateHtmlBody();
//        }else {
//            String htmlBody = emailTemplateService.generateEmail(request.templateName(),variables);
//
//        }

        for (int i = 1; i < request.dynamicVariables().length + 1; i++) {
            variables.put("Value_" + String.valueOf(i),request.dynamicVariables()[i - 1]);
        }

        String htmlBody = emailTemplateService.generateEmail(request.templateName(),variables);

        if (receivingGroup.trim().isEmpty()) {
            to.addAll(guestRepository.findAll());
            to.addAll(adminRepository.findAll());
        } else if (receivingGroup.toLowerCase().trim().equals("admin")) {
            to.addAll(adminRepository.findAll());
        }else {
            to.addAll(guestRepository.findAll());
        }


        to.forEach(u -> {
            sendEmail(request.from(),request.subject(),htmlBody,u);
        });
    }

//    public void sendPlainTextEmail(Emails from,String subject,String text) {
//        sendEmail(from,subject,text);
//    }
}
