package mailService.kninit.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import mailService.kninit.Enum.Emails;
import mailService.kninit.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailTemplateServiceImpl emailTemplateService;


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
    public void sendEmail(Emails from, String subject, String body) {
        boolean isHtml;

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        if (body.startsWith("<!DOCTYPE html>")) {

            isHtml = true;
        } else {

            isHtml = false;
        }

        userRepository.findAll().forEach(user -> {
            try {

                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setFrom(from.getEmail());
                helper.setTo(user.getEmails().get(0));
                helper.setSubject(subject);
                helper.setText(body,isHtml);
                javaMailSender.send(mimeMessage);

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

        });


    }
    public void sendVerificationEmail(Emails from,String subject,String verificationLink) {

        Map<String,Object> variables = Map.of(
           "VerificationLink", verificationLink
        );

        String htmlBody = emailTemplateService.generateEmail("icc_account_verification",variables);

        sendEmail(from,subject,htmlBody);
    }
    public void sendPlainTextEmail(Emails from,String subject,String text) {
        sendEmail(from,subject,text);
    }
}
