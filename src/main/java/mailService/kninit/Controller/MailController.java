package mailService.kninit.Controller;

import lombok.AllArgsConstructor;
import mailService.kninit.Entitie.Request;
import mailService.kninit.Service.MailSenderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@AllArgsConstructor
public class MailController {

    @Autowired
    MailSenderServiceImpl emailService;

    @PostMapping("/sendVerificationEmail")
    public void sendVerificationEmail (@RequestBody Request.SendVerificationEmailRequest request) {
        emailService.sendVerificationEmail(request.from(),request.subject(),request.verificationLink());
    }

    @PostMapping("/sendPlainTextEmail")
    public void sendPlainTextEmail (@RequestBody Request.SendPlainTextEmailRequest request) {
        emailService.sendPlainTextEmail(request.from(), request.subject(),request.body());
    }

}
