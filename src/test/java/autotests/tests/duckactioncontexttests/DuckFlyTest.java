package autotests.tests.duckactioncontexttests;

import autotests.clients.DuckActionController;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static autotests.constants.FilePaths.DUCK_WITH_ACTIVE_WINGS_PATH;
import static autotests.constants.FilePaths.DUCK_WITH_FIXED_WINGS_PATH;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

public class DuckFlyTest extends DuckActionController {

    private final String ACTIVE_WINGS_MESSAGE = "{\n" + "\"message\": \"I'm flying\"\n" + "}";
    private final String FIXED_WINGS_MESSAGE = "{\n" + "\"message\": \"I can’t fly\"\n" + "}";

    @Test(description = "Проверка корректности сообщения тела ответа при активных крыльях")
    @CitrusTest
    public void checkDuckFlyMessageWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        runner.$(doFinally().actions(context -> deleteDuckViaDeleteRequest(runner, "${duckId}")));
        createDuckFromFileViaPostRequest(runner, DUCK_WITH_ACTIVE_WINGS_PATH);
        extractIdFromBody(runner);
        duckFly(runner, "${duckId}");
        validateResponse(runner, yellowDuckService, HttpStatus.OK, ACTIVE_WINGS_MESSAGE);
    }

    @Test(description = "Проверка корректности сообщения тела ответа при фиксированных крыльях")
    @CitrusTest
    public void checkDuckFlyMessageWithFixedWings(@Optional @CitrusResource TestCaseRunner runner) {
        runner.$(doFinally().actions(context -> deleteDuckViaDeleteRequest(runner, "${duckId}")));
        createDuckFromFileViaPostRequest(runner, DUCK_WITH_FIXED_WINGS_PATH);
        extractIdFromBody(runner);
        duckFly(runner, "${duckId}");
        validateResponse(runner, yellowDuckService, HttpStatus.OK, FIXED_WINGS_MESSAGE);
    }
}