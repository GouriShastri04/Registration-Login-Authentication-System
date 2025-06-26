package login.page.registration_login_authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync

@EntityScan({"login.page.registration_login_authentication.entity", "login.page.registration_login_authentication.model"})
@EnableJpaRepositories("login.page.registration_login_authentication.repository")
@ComponentScan("login.page.registration_login_authentication")

public class RegistrationLoginAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationLoginAuthenticationApplication.class, args);
	}

}
