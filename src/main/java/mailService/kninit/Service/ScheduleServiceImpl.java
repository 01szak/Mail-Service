package mailService.kninit.Service;

import lombok.AllArgsConstructor;
import mailService.kninit.Entitie.EmailTemplate;
import mailService.kninit.Entitie.Request;
import mailService.kninit.Entitie.Schedule;
import mailService.kninit.Enum.ScheduleStatus;
import mailService.kninit.Repository.ScheduleRepository;
import org.bson.types.ObjectId;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {


    private final ScheduleRepository scheduleRepository;

    private final EmailTemplateServiceImpl emailTemplateService;

    private final MailSenderServiceImpl mailSenderService;

//    private static List<Schedule> schedules = new ArrayList<>();

    @Override
    public void scheduleEmail(List<String> receivingGroups,LocalDateTime date, Request.SendEmailToAllRequest request) {
        EmailTemplate emailTemplate = new EmailTemplate();

        if (request.newTemplate() == null) {
            if (emailTemplateService.findByTemplateName(request.templateName()) == null) {
                throw new IllegalStateException("Template not found or incorrect template name");
            }else {
                emailTemplate = emailTemplateService.findByTemplateName(request.templateName());
            }
        }else {
            emailTemplateService.saveTemplate(request.newTemplate());
            emailTemplate = emailTemplateService.findByTemplateName(request.newTemplate().templateName());
        }
        Schedule schedule = scheduleRepository.save(Schedule.builder()
                .date(date)
                .from(request.from())
                .templateId(emailTemplate.getId())
                .scheduleStatus(ScheduleStatus.PENDING)
                .dynamicVariables(request.dynamicVariables())
                .receivingGroups(receivingGroups)
                .build());

    }
//    @Scheduled(fixedRate = 3600000)
//    private void fillSchedulesWithNewData() {
//        schedules = scheduleRepository.findAll();
//    }

    @Override
    @Scheduled(fixedRate = 30000)
    public void checkScheduledEmails() {
//        ScheduleServiceImpl.schedules.removeIf(schedule ->  schedule.getScheduleStatus().equals(ScheduleStatus.SENT));
        scheduleRepository.findAll().forEach(schedule -> {
            if (schedule.getScheduleStatus().equals(ScheduleStatus.SENT)) {
                scheduleRepository.deleteById(schedule.getId());
            }
        });

        List<Schedule> activeSchedules = scheduleRepository.findAll();

        activeSchedules.forEach(schedule -> {

            LocalDateTime scheduled = schedule.getDate().withSecond(0).withNano(0);
            LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
            Duration difference = Duration.between(now,scheduled);

            if(Math.abs(difference.toMinutes()) == 0) {
                try {
                    EmailTemplate emailTemplate = emailTemplateService.findById(schedule.getTemplateId());
                    Request.SendEmailToAllRequest request = new Request.SendEmailToAllRequest(null, schedule.getFrom(), emailTemplate.getTemplateName(), schedule.getDynamicVariables());
                    mailSenderService.sendEmailToAll(schedule.getReceivingGroups(), request);
                    schedule.setScheduleStatus(ScheduleStatus.SENT);
                    scheduleRepository.save(schedule);
                    System.out.println("mail został wysłany");

                }catch (Exception e) {
                    schedule.setScheduleStatus(ScheduleStatus.FAILED);
                    scheduleRepository.save(schedule);
                    String  errorMessage = e.getMessage() + "\nfailed to send mail {\nid -> (%s)  \nreceiving group/s -> %s}".formatted(schedule.getId(),schedule.getReceivingGroups());
                    throw new IllegalStateException(errorMessage);
                }

            }
        });

    }

    @Override
    public void deleteScheduleByScheduleId(ObjectId id) {
        scheduleRepository.deleteById(id);
    }
}
