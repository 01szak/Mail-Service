package mailService.kninit.Service;

import mailService.kninit.Entitie.Request;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {
    void scheduleEmail(List<String> receivingGroups, LocalDateTime date, Request.SendEmailToAllRequest request);

    void checkScheduledEmails();
}
