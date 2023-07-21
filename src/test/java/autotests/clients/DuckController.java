package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckController extends TestNGCitrusSpringSupport {

    private final TestCaseRunner RUNNER;

    @Autowired
    protected HttpClient yellowDuckService;

    public DuckController(TestCaseRunner runner) {
        this.RUNNER = runner;
    }

    public void createDuck(String color, String height, String material, String sound, String wingsState) {
        RUNNER.$(http().client(yellowDuckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "  \"color\": " + color + ",\n" +
                        "  \"height\": " + height + ",\n" +
                        "  \"material\": " + material + ",\n" +
                        "  \"sound\": " + sound + ",\n" +
                        "  \"wingsState\": " + wingsState + "\n" +
                        "}")
        );
    }

    public void updateDuck(String color, String height, String id, String material, String sound, String wingsState) {
        RUNNER.$(http().client(yellowDuckService)
                .send()
                .put("/api/duck/update")
                .queryParam("color", color)
                .queryParam("height", height)
                .queryParam("id", id)
                .queryParam("material", material)
                .queryParam("sound", sound)
                .queryParam("wingsState", wingsState)
        );
    }

    public void deleteDuck(String id) {
        RUNNER.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/delete")
                .queryParam("id", id)
        );
    }

    public void getDucksIds() {
        RUNNER.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/getAllIds")
        );
    }

    @Description("Response validation with body as string")
    public void validateResponseAsString(String expectedJsonString) {
        RUNNER.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message().type(MessageType.JSON)
                .body(expectedJsonString)
        );
    }

    @Description("Response validation with JSON file")
    public void validateResponseFromResourceFolder(String expectedPayload) {
        RUNNER.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message().type(MessageType.JSON)
                .body(new ClassPathResource(expectedPayload))
        );
    }

    @Description("Response validation with POJO")
    public void validateResponseFromModel(Object model) {
        RUNNER.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message().type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(model, new ObjectMapper()))
        );
    }
}