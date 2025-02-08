package co.yapp.orbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class OrbitApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrbitApplication.class, args);
	}

}
