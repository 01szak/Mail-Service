package mailService.kninit.Services;

import lombok.AllArgsConstructor;
import mailService.kninit.Services.EmailService;
import mailService.kninit.Enums.Emails;
import mailService.kninit.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    @Async
    @Override
    public void sendEmail(Emails from, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        userRepository.findAll().forEach(user -> {

        message.setTo(user.getEmails().get(0));
        message.setFrom(from.getEmail());
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    });



    }
}
