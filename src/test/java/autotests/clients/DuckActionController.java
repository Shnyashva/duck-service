package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
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

    public void duckFly(String id) {
        RUNNER.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id)
        );
    }

    public void duckProperties(String id) {
        RUNNER.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id)
        );
    }

    public void duckQuack(String id, String repetitionCount, String soundCount) {
        RUNNER.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount", repetitionCount)
                .queryParam("soundCount", soundCount)
        );
    }

    public void duckSwim(String id) {
        RUNNER.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id)
        );
    }

    @Description("Response validation as string")
    public void validateResponseAsString(String expectedJsonString) {
        RUNNER.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message().type(MessageType.JSON)
                .body(expectedJsonString)
        );
    }

    @Description("Response validation from resource folder")
    public void validateResponseFromResourceFolder(String expectedPayload) {
        RUNNER.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message().type(MessageType.JSON)
                .body(new ClassPathResource(expectedPayload))
        );
    }

    @Description("Response validation from POJO")
    public void validateResponseFromModel(String expectedPayload) {
        RUNNER.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message().type(MessageType.JSON)
                .body(new ClassPathResource(expectedPayload))
        );
    }
}