package za.co.univen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages = {"za.co.univen.its.reviews.entities"})
public class AccessReviewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessReviewsApplication.class, args);
	}

}
