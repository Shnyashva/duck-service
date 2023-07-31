package autotests;

import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.http.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class EndpointConfig {

    @Bean
    public HttpClient yellowDuckService() {
        return new HttpClientBuilder()
                .requestUrl("http://localhost:2222")
                .build();
    }

    @Bean
    public SingleConnectionDataSource ducksDataBase() {
        return new SingleConnectionDataSource() {{
            setDriverClassName("org.h2.Driver");
            setUrl("jdbc:h2:tcp://localhost:9092/mem:ducks");
            setUsername("dev");
            setPassword("dev");
        }};
    }
}