package mailService.kninit.Controller;

import lombok.AllArgsConstructor;
import mailService.kninit.Entitie.SendRequest;
import mailService.kninit.Service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@AllArgsConstructor
public class MailController {

    @Autowired
    EmailServiceImpl emailService;
    @GetMapping("/send")
    public void send (SendRequest request){
        emailService.sendEmail(request.from(),request.subject(),request.body());
    }
    @GetMapping("/sendtest")
    public void sendTest (){
        emailService.testSendMail();

    }

}
