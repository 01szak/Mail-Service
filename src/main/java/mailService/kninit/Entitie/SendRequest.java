package mailService.kninit.Entitie;

import mailService.kninit.Enum.Emails;

public record SendRequest(Emails from, String subject, String body ) {}
