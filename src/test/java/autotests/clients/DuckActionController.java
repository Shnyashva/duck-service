package autotests.clients;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;

import static autotests.constants.SqlQueries.*;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckActionController extends BaseTest {

    protected void duckFly(TestCaseRunner runner, String id) {
        sendGetRequest(runner, yellowDuckService, "/api/duck/action/fly", "id", id);
    }

    protected void duckSwim(TestCaseRunner runner, String id) {
        sendGetRequest(runner, yellowDuckService, "/api/duck/action/swim", "id", id);
    }

    protected void duckProperties(TestCaseRunner runner, String id) {
        sendGetRequest(runner, yellowDuckService, "/api/duck/action/properties", "id", id);
    }

    protected void duckQuack(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount", repetitionCount)
                .queryParam("soundCount", soundCount)
        );
    }

    protected void createDuckFromFileViaPostRequest(TestCaseRunner runner, String filePath) {
        sendPostRequestFromFile(runner, yellowDuckService, "/api/duck/create", filePath);
    }

    protected void createDuckFromPayloadViaPostRequest(TestCaseRunner runner, Object model) {
        sendPostRequestAsPayload(runner, yellowDuckService, "/api/duck/create", model);
    }

    protected void createDuckViaSqlQuery(TestCaseRunner runner) {
        setDuckIdVariable(runner);
        updateDataBase(runner, ducksDataBase, CREATE_TEST_DUCK_QUERY);
    }

    protected void deleteDuckViaDeleteRequest(TestCaseRunner runner, String id) {
        sendDeleteRequest(runner, yellowDuckService, "/api/duck/delete", "id", id);
    }

    protected void deleteDuckViaSqlQuery(TestCaseRunner runner) {
        updateDataBase(runner, ducksDataBase, DELETE_DUCK_FROM_TABLE_QUERY);
    }
}