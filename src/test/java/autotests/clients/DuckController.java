package autotests.clients;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;

import static autotests.constants.SqlQueries.*;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckController extends BaseTest {

    protected void updateDuck(TestCaseRunner runner, String color, String height, String id, String material, String sound, String wingsState) {
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

    protected void getDucksIds(TestCaseRunner runner) {
        sendGetRequest(runner, yellowDuckService, "/api/duck/getAllIds");
    }

    protected void createDuckFromFile(TestCaseRunner runner, String filePath) {
        sendPostRequestFromFile(runner, yellowDuckService, "/api/duck/create", filePath);
    }

    protected void createDuckFromPayload(TestCaseRunner runner, Object model) {
        sendPostRequestAsPayload(runner, yellowDuckService, "/api/duck/create", model);
    }

    protected void createDuckFromString(TestCaseRunner runner, String body) {
        sendPostRequestAsString(runner, yellowDuckService, "/api/duck/create", body);
    }

    protected void deleteDuckViaSqlQuery(TestCaseRunner runner) {
        updateDataBase(runner, ducksDataBase, DELETE_DUCK_FROM_TABLE_QUERY);
    }

    protected void deleteDuckViaDeleteRequest(TestCaseRunner runner, String id) {
        sendDeleteRequest(runner, yellowDuckService, "/api/duck/delete", "id", id);
    }

    protected void createDuckViaSqlQuery(TestCaseRunner runner) {
        setDuckIdVariable(runner);
        updateDataBase(runner, ducksDataBase, CREATE_TEST_DUCK_QUERY);
    }

    protected void clearTable(TestCaseRunner runner) {
        updateDataBase(runner, ducksDataBase, CLEAR_TABLE);
    }
}