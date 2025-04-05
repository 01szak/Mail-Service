package mailService.kninit.Controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import mailService.kninit.Entitie.Request;
import mailService.kninit.Service.MailSenderServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
@AllArgsConstructor
public class MailController {

    private final MailSenderServiceImpl emailService;

    @PostMapping("/sendVerificationEmail")
    public ResponseEntity sendVerificationEmail (@RequestBody Request.SendVerificationEmailRequest request) {
        emailService.sendVerificationEmail(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("sendEmailToAll/{receivingGroup}")
    public ResponseEntity sendEmailToAll (@Parameter(description = "Grupa odbiorców ") @PathVariable("receivingGroup") String receivingGroup, @RequestBody Request.SendEmailToAllRequest request) {
        emailService.sendEmailToAll(receivingGroup,request);
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/sendPlainTextEmail")
//    public void sendPlainTextEmail (@RequestBody Request.SendPlainTextEmailRequest request) {
//        emailService.sendPlainTextEmail(request.from(), request.subject(),request.body());
//    }

}
