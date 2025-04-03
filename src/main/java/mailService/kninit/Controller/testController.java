package mailService.kninit.Controller;

import lombok.AllArgsConstructor;
import mailService.kninit.Entitie.EmailTemplate;
import mailService.kninit.Entitie.User;
import mailService.kninit.Enum.Emails;
import mailService.kninit.Repository.EmailTemplateRepository;
import mailService.kninit.Repository.UserRepository;
import mailService.kninit.Service.MailSenderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class testController {

  @Autowired
  MailSenderServiceImpl emailService;
  @Autowired
  UserRepository userRepository;
  EmailTemplateRepository emailTemplateRepository;
    @GetMapping("/test")
    public void test () {
         emailService.sendEmail(Emails.KONTAKT, "testowy email","to jest testowy email" );
    }
    @GetMapping("/showUsers")
    public List<User> showUsers () {
      System.out.println(userRepository.findAll());
        return userRepository.findAll();
    }
    @GetMapping("/showEmailTemplate")
    public List<EmailTemplate> showEmailTemplate () {
      System.out.println(emailTemplateRepository.findAll() );
        return emailTemplateRepository.findAll() ;
    }
    @GetMapping("/helloWorld")
    public String helloWorld () {
      return "hello world";
    }
}
