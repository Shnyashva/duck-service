package autotests.clients;

import autotests.BaseTest;
import autotests.interfaces.ICreateDuck;
import com.consol.citrus.TestCaseRunner;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckController extends BaseTest implements ICreateDuck {

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

    @Override
    public void createDuckFromFile(TestCaseRunner runner, String filePath) {
        sendPostRequestFromFile(runner, yellowDuckService, "/api/duck/create", filePath);
    }

    @Override
    public void createDuckFromPayload(TestCaseRunner runner, Object model) {
        sendPostRequestAsPayload(runner, yellowDuckService, "/api/duck/create", model);
    }

    @Override
    public void createDuckFromString(TestCaseRunner runner, String body) {
        sendPostRequestAsString(runner, yellowDuckService, "/api/duck/create", body);
    }
}