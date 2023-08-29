package atipera.interview.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebClientConfig implements WebMvcConfigurer {

    @Value("${github.pat:#{null}}")
    private String privateAccessToken;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .defaultHeaders(httpHeaders -> {
                    if (privateAccessToken != null) {
                        httpHeaders.put(
                                HttpHeaders.AUTHORIZATION,
                                List.of("Bearer" + privateAccessToken)
                        );
                    }
                })
                .build();
    }
}
