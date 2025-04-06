package mailService.kninit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("sandbox.smtp.mailtrap.io");
        mailSender.setPort(2525);
        mailSender.setUsername("5b016d7c652f4e");
        mailSender.setPassword("e99f27972bd9b8");

        System.out.println("Mail Sender Configured:");
        System.out.println("Host: " + mailSender.getHost());
        System.out.println("Port: " + mailSender.getPort());
        System.out.println("Username: " + mailSender.getUsername());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}

