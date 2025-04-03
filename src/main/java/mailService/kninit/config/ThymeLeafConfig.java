package mailService.kninit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import static org.thymeleaf.templatemode.TemplateMode.HTML;

@Configuration
public class ThymeLeafConfig {
    @Bean
    @Primary
    public TemplateEngine templateEngine () {
     TemplateEngine templateEngine = new TemplateEngine();
     StringTemplateResolver resolver = new StringTemplateResolver();

     resolver.setTemplateMode(HTML);
     resolver.setCacheable(false);
     resolver.setOrder(0);

     templateEngine.setTemplateResolver(resolver);

     return templateEngine;
    }

}
