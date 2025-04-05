package mailService.kninit.Controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import mailService.kninit.Entitie.Request;
import mailService.kninit.Service.MailSenderServiceImpl;
import mailService.kninit.Service.ScheduleServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/mail")
@AllArgsConstructor
public class MailController {

    private final MailSenderServiceImpl emailService;

    private final ScheduleServiceImpl scheduleService;
    @PostMapping("/sendVerification")
    public ResponseEntity sendVerificationEmail (@RequestBody Request.SendVerificationEmailRequest request) {
        emailService.sendVerificationEmail(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("sendToAll/{receivingGroup}")
    public ResponseEntity sendEmailToAll (@Parameter(description = "Receiving Group ") @PathVariable("receivingGroup") List<String> receivingGroup,
                                          @RequestBody Request.SendEmailToAllRequest request) {
        emailService.sendEmailToAll(receivingGroup,request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/schedule/{receivingGroup}/{date}")
    public ResponseEntity scheduleEmail (@Parameter(description = "Receiving Group ") @PathVariable("receivingGroup") List<String> receivingGroups,
                                         @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                                         @RequestBody Request.SendEmailToAllRequest request){
        scheduleService.scheduleEmail(receivingGroups,date,request);
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/sendPlainTextEmail")
//    public void sendPlainTextEmail (@RequestBody Request.SendPlainTextEmailRequest request) {
//        emailService.sendPlainTextEmail(request.from(), request.subject(),request.body());
//    }

}
