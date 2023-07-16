package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckActionController extends TestNGCitrusSpringSupport  {

    private final TestCaseRunner RUNNER;

    @Autowired
    protected HttpClient yellowDuckService;

    public DuckActionController(TestCaseRunner runner) {
        RUNNER = runner;
    }

    public void duckFly(int id) {
        RUNNER.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", String.valueOf(id))
        );
    }

    public void duckProperties(int id) {
        RUNNER.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", String.valueOf(id))
        );
    }

    public void duckQuack(int id, int repetitionCount, int soundCount) {
        RUNNER.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", String.valueOf(id))
                .queryParam("repetitionCount", String.valueOf(repetitionCount))
                .queryParam("soundCount", String.valueOf(soundCount))
        );
    }

    public void duckSwim(int id) {
        RUNNER.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", String.valueOf(id))
        );
    }
}