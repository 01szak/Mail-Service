package mailService.kninit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MailServiceApplication {

	public static void main(String[] args) {
		System.out.println(System.getenv("SPRING_DATASOURCE_URL"));

		SpringApplication.run(MailServiceApplication.class, args);

	}

}
