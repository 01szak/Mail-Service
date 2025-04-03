package mailService.kninit.Entitie;

import mailService.kninit.Enum.Emails;

public class Request {
    public record SendVerificationEmailRequest(Emails from, String subject, String verificationLink ) {}
    public record SendPlainTextEmailRequest(Emails from, String subject, String body) {}

}
