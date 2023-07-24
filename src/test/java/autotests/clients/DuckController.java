package autotests.clients;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckController extends BaseTest {

    public void updateDuck(TestCaseRunner runner, String color, String height, String id, String material, String sound, String wingsState) {
        runner.$(http().client(yellowDuckService)
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

    public void getDucksIds(TestCaseRunner runner) {
        sendGetRequest(runner, yellowDuckService, "/api/duck/getAllIds");
    }
}