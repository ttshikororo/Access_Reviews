package za.co.univen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
@EntityScan(basePackages = {"za.co.univen.its.reviews.entities"})
public class AccessReviewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessReviewsApplication.class, args);
	}

}
