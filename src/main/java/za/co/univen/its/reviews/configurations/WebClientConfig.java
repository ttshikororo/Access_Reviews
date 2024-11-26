package za.co.univen.its.reviews.configurations;

import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient() {



        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(basicAuthentication("430" , "71654"))
                   .exchangeStrategies(ExchangeStrategies.builder().codecs(
                    clientCodecConfigurer ->
                            clientCodecConfigurer.defaultCodecs().maxInMemorySize(1000000000))

            .build())

                .build();
    }




}
