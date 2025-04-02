package mailService.kninit.Services;

import mailService.kninit.Enums.Emails;

public interface EmailService {
    void sendEmail(Emails from, String subject, String body);
}
