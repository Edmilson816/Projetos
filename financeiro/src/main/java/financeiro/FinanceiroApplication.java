package financeiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = "financeiro.model")
@ComponentScan(basePackages = "financeiro.*")
@EnableJpaRepositories(basePackages = {"financeiro.repository"})
@EnableTransactionManagement
@EnableWebMvc
public class FinanceiroApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(FinanceiroApplication.class, args);
		
		/*BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String result = encoder.encode("123");
		System.out.println(result);*/
					 
	}      //https://www.youtube.com/watch?v=9Zl0uMsqu7U   dica para uso do git YOUTUBE
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

		registry.addViewController("/login").setViewName("/login");
		registry.setOrder(Ordered.LOWEST_PRECEDENCE);
	}

	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	  registry.addMapping("/paciente**").allowedMethods("*").allowedOrigins("*");	
		
	}

}
