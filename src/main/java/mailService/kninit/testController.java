package mailService.kninit;

import lombok.AllArgsConstructor;
import mailService.kninit.Entities.User;
import mailService.kninit.Enums.Emails;
import mailService.kninit.Repositories.UserRepository;
import mailService.kninit.Services.EmailServiceImpl;
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
