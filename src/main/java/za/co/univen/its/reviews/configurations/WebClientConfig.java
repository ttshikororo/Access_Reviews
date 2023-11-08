package za.co.univen.its.reviews.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient()
    {
        return WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder().codecs(
                    clientCodecConfigurer ->
                            clientCodecConfigurer.defaultCodecs().maxInMemorySize(1000000000))
            .build())
                .build();
    }




}
