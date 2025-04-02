package mailService.kninit.Service;

import jakarta.mail.MessagingException;
import mailService.kninit.Enum.Emails;

public interface EmailService {
    void sendEmail(Emails from, String subject, String body) throws MessagingException;
}
