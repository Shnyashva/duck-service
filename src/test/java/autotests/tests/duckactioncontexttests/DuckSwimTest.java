package autotests.tests.duckactioncontexttests;

import autotests.clients.DuckActionController;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static autotests.constants.FilePaths.DUCK_WITH_ACTIVE_WINGS_PATH;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

public class DuckSwimTest extends DuckActionController {
    private final String MESSAGE = "{\n" + "\"message\": \"I'm swimming\"\n" + "}";

    @Test(description = "Проверка корректности сообщения в теле ответа")
    @CitrusTest
    public void checkDuckSwimMessageWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        runner.$(doFinally().actions(context -> deleteDuck(runner, "${duckId}")));
        createDuckFromFile(runner, DUCK_WITH_ACTIVE_WINGS_PATH);
        extractIdFromBody(runner);
        duckSwim(runner, "${duckId}");
        validateResponse(runner, yellowDuckService, HttpStatus.OK, MESSAGE);
    }
}