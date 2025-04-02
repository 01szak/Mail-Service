package mailService.kninit.Controller;

import lombok.AllArgsConstructor;
import mailService.kninit.Entitie.User;
import mailService.kninit.Enum.Emails;
import mailService.kninit.Repository.UserRepository;
import mailService.kninit.Service.EmailServiceImpl;
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
  EmailServiceImpl emailService;
  @Autowired
  UserRepository userRepository;
    @GetMapping("/test")
    public void test () {
         emailService.sendEmail(Emails.KONTAKT, "testowy email","to jest testowy email" );
    }
    @GetMapping("/showUsers")
    public List<User> showUsers () {
        return userRepository.findAll();
    }
}
