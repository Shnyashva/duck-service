package autotests.clients;

import autotests.BaseTest;
import autotests.interfaces.ICreateDuck;
import com.consol.citrus.TestCaseRunner;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckActionController extends BaseTest implements ICreateDuck {

    public void duckFly(TestCaseRunner runner, String id) {
        sendGetRequest(runner, yellowDuckService, "/api/duck/action/fly", "id", id);
    }

    public void duckSwim(TestCaseRunner runner, String id) {
        sendGetRequest(runner, yellowDuckService, "/api/duck/action/swim", "id", id);
    }

    public void duckProperties(TestCaseRunner runner, String id) {
        sendGetRequest(runner, yellowDuckService, "/api/duck/action/properties", "id", id);
    }

    public void duckQuack(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount", repetitionCount)
                .queryParam("soundCount", soundCount)
        );
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