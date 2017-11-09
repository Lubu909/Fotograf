package pl.edu.ug.fotograf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.ug.fotograf.config.SpringWebAppInitializer;

@SpringBootApplication
public class FotografApplication {

	public static void main(String[] args) {
		SpringApplication.run(new Class[] { FotografApplication.class, SpringWebAppInitializer.class }, args);
	}
}
