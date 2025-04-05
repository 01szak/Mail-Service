package mailService.kninit.Controller;

import lombok.AllArgsConstructor;
import mailService.kninit.Entitie.Request;
import mailService.kninit.Service.EmailTemplateServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/template")
@AllArgsConstructor
public class EmailTemplateController {

    private final EmailTemplateServiceImpl emailTemplateService;

    @PostMapping("/save")
    public ResponseEntity saveTemplate (@RequestBody Request.CreateTemplateRequest request) {
        emailTemplateService.saveTemplate(request);
        return ResponseEntity.ok().build();
    }
}
