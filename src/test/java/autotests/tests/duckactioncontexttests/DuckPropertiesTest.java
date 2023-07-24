package autotests.tests.duckactioncontexttests;

import autotests.clients.DuckActionController;
import autotests.payloads.Duck;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static autotests.constants.FilePaths.*;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

public class DuckPropertiesTest extends DuckActionController {

    private final Duck WOOD_DUCK = new Duck().color("blue").height(10.0).material("wood")
            .sound("quack").wingsState("ACTIVE");

    @Test(description = "Проверка корректности тела ответа при активных крыльях. material = rubber")
    @CitrusTest
    public void checkDuckPropertiesMessageWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        runner.$(doFinally().actions(context -> deleteDuck(runner, "${duckId}")));
        createDuckFromFile(runner, DUCK_WITH_ACTIVE_WINGS_PATH);
        extractIdFromBody(runner);
        duckProperties(runner, "${duckId}");
        validateResponseFromFile(runner, DUCK_WITH_ACTIVE_WINGS_PATH);
    }

    @Test(description = "Проверка корректности тела ответа при фиксированных крыльях. material = rubber")
    @CitrusTest
    public void checkDuckPropertiesMessageWithFixedWings(@Optional @CitrusResource TestCaseRunner runner) {
        runner.$(doFinally().actions(context -> deleteDuck(runner, "${duckId}")));
        createDuckFromFile(runner, DUCK_WITH_FIXED_WINGS_PATH);
        extractIdFromBody(runner);
        duckProperties(runner, "${duckId}");
        validateResponseFromFile(runner, DUCK_WITH_FIXED_WINGS_PATH);
    }

    @Test(description = "Проверка корректности тела ответа при неопределенных крыльях. material = rubber")
    @CitrusTest
    public void checkDuckPropertiesMessageWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        runner.$(doFinally().actions(context -> deleteDuck(runner, "${duckId}")));
        createDuckFromFile(runner, DUCK_WITH_UNDEFINED_WINGS_PATH);
        extractIdFromBody(runner);
        duckProperties(runner, "${duckId}");
        validateResponseFromFile(runner, DUCK_WITH_UNDEFINED_WINGS_PATH);
    }

    @Test(description = "Проверка корректности тела ответа в случае материала утки, отличного от rubber")
    @CitrusTest
    public void checkDuckPropertiesMessageWithAnotherMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        runner.$(doFinally().actions(context -> deleteDuck(runner, "${duckId}")));
        createDuckFromPayload(runner, WOOD_DUCK);
        extractIdFromBody(runner);
        duckProperties(runner, "${duckId}");
        validateResponseFromModel(runner, WOOD_DUCK);
    }
}