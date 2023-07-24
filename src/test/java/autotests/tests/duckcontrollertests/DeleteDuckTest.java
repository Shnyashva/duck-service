package autotests.tests.duckcontrollertests;

import autotests.clients.DuckController;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static autotests.constants.FilePaths.DUCK_WITH_ACTIVE_WINGS_PATH;

public class DeleteDuckTest extends DuckController {

    private final String MESSAGE = "Duck is deleted";

    @Test(description = "Проверка возможности удаления существующей в базе данных утки")
    @CitrusTest
    public void deleteExistedDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckFromFile(runner, DUCK_WITH_ACTIVE_WINGS_PATH);
        extractIdFromBody(runner);
        deleteDuck(runner, "${duckId}");
        validateResponseAsJsonPath(runner, yellowDuckService, HttpStatus.OK, "$.message", MESSAGE);
    }

    @Test(description = "Проверка возможности удаления несуществующей в базе данных утки")
    @CitrusTest
    public void deleteNonExistedDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckFromFile(runner, DUCK_WITH_ACTIVE_WINGS_PATH);
        extractIdFromBody(runner);
        deleteDuck(runner, "${duckId}");
        deleteDuck(runner, "${duckId}");
        validateResponse(runner, yellowDuckService, HttpStatus.OK);
    }
}