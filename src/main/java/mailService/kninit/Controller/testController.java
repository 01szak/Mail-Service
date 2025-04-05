package mailService.kninit.Controller;

import lombok.AllArgsConstructor;
import mailService.kninit.Entitie.Admin;
import mailService.kninit.Entitie.EmailTemplate;
import mailService.kninit.Entitie.Guest;
import mailService.kninit.Repository.AdminRepository;
import mailService.kninit.Repository.EmailTemplateRepository;
import mailService.kninit.Repository.GuestRepository;
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
  GuestRepository guestRepository;
  EmailTemplateRepository emailTemplateRepository;
  AdminRepository adminRepository;

//    @GetMapping("/test")
//    public void test () {
//         emailService.sendEmail(Emails.KONTAKT, "testowy email","to jest testowy email" );
//    }
    @GetMapping("/showUsers")
    public List<Guest> showUsers () {
        return guestRepository.findAll();
    }
    @GetMapping("/showEmailTemplate")
    public List<EmailTemplate> showEmailTemplate () {
        return emailTemplateRepository.findAll() ;
    }
    @GetMapping("/showAdmins")
    public List<Admin> showAdmins () {
        return adminRepository.findAll() ;
    }

    @GetMapping("/helloWorld")
    public String helloWorld () {
      return "dasdsad world";
    }
}
