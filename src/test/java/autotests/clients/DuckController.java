package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void createDuck(String color, double height, String material, String sound, String wingsState) {
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

    public void updateDuck(String color, double height, int id, String material, String sound, String wingsState) {
        RUNNER.$(http().client(yellowDuckService)
                .send()
                .put("/api/duck/update")
                .queryParam("color", color)
                .queryParam("height", String.valueOf(height))
                .queryParam("id", String.valueOf(id))
                .queryParam("material", material)
                .queryParam("sound", sound)
                .queryParam("wingsState", wingsState)
        );
    }

    public void deleteDuck(int id) {
        RUNNER.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/delete")
                .queryParam("id", String.valueOf(id))
        );
    }

    public void getDucksIds() {
        RUNNER.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/getAllIds")
        );
    }
}