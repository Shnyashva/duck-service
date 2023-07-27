package autotests.tests.duckcontrollertests;

import autotests.clients.DuckController;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static autotests.constants.FilePaths.DUCK_WITH_ACTIVE_WINGS_PATH;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

public class UpdateDuckTest extends DuckController {

    private final String MESSAGE = "{\n" +
            "\"message\": \"Duck with id = ${duckId} is updated\"\n" +
            "}";

    @Test(description = "Проверка корректности изменения свойства утки")
    @CitrusTest
    public void updateExistDuck(@Optional @CitrusResource TestCaseRunner runner) {
        runner.$(doFinally().actions(context -> deleteDuckViaDeleteRequest(runner, "${duckId}")));
        createDuckFromFile(runner, DUCK_WITH_ACTIVE_WINGS_PATH);
        extractIdFromBody(runner);
        updateDuck(runner, "green", "100", "${duckId}", "metal", "mya", "FIXED");
        validateDuckInDatabase(runner, "${duckId}", "green", "100.0", "metal", "mya", "FIXED");
    }
}