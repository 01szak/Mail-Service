package mailService.kninit.Service;

import jakarta.mail.MessagingException;
import mailService.kninit.Entitie.User;
import mailService.kninit.Enum.Emails;

public interface MailSenderService {
    void sendEmail(Emails from, String subject, String body, User user) throws MessagingException;
}
