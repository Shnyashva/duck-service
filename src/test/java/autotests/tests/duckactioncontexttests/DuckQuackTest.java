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

public class DuckQuackTest extends DuckActionController {

    private final String EMPTY_MESSAGE = "";
    private final String RESPONSE_MESSAGE = "quack-quack, quack-quack, quack-quack";
    private final String RESPONSE_MESSAGE_2 = "quack-quack-quack, quack-quack-quack";
    private final String RESPONSE_MESSAGE_3 = "quack-quack-quack, quack-quack-quack, quack-quack-quack";
    private final String RESPONSE_MESSAGE_4 = "";

    @Test(description = "Проверка корректности количества звуков в теле ответапри передаче нулевого количества повторов")
    @CitrusTest
    public void checkMessageWithoutSoundRepetition(@Optional @CitrusResource TestCaseRunner runner) {
        runner.$(doFinally().actions(context -> deleteDuckViaDeleteRequest(runner, "${duckId}")));
        createDuckFromFileViaPostRequest(runner, DUCK_WITH_ACTIVE_WINGS_PATH);
        extractIdFromBody(runner);
        duckQuack(runner, "${duckId}", "0", "2");
        validateResponseAsJsonPath(runner, yellowDuckService, HttpStatus.OK, "$.sound", EMPTY_MESSAGE);
    }

    @Test(description = "Проверка корректности количества звуков в теле ответа при передаче двух повторов и трех звуков")
    @CitrusTest
    public void checkMessageWithTwoRepetitionAndThreeQuacks(@Optional @CitrusResource TestCaseRunner runner) {
        runner.$(doFinally().actions(context -> deleteDuckViaDeleteRequest(runner, "${duckId}")));
        createDuckFromFileViaPostRequest(runner, DUCK_WITH_ACTIVE_WINGS_PATH);
        extractIdFromBody(runner);
        duckQuack(runner, "${duckId}", "2", "3");
        validateResponseAsJsonPath(runner, yellowDuckService, HttpStatus.OK, "$.sound", RESPONSE_MESSAGE);
    }

    @Test(description = "Проверка корректности количества звуков в теле ответа при передаче трех повторов и двух звуков")
    @CitrusTest
    public void checkMessageWithThreeRepetitionAndTwoQuacks(@Optional @CitrusResource TestCaseRunner runner) {
        runner.$(doFinally().actions(context -> deleteDuckViaDeleteRequest(runner, "${duckId}")));
        createDuckFromFileViaPostRequest(runner, DUCK_WITH_ACTIVE_WINGS_PATH);
        extractIdFromBody(runner);
        duckQuack(runner, "${duckId}", "3", "2");
        validateResponseAsJsonPath(runner, yellowDuckService, HttpStatus.OK, "$.sound", RESPONSE_MESSAGE_2);
    }

    @Test(description = "Проверка корректности количества звуков в теле ответа при одинаковом количестве повторов и звуков")
    @CitrusTest
    public void checkMessageWithTheSameRepetitionAndQuackCounts(@Optional @CitrusResource TestCaseRunner runner) {
        runner.$(doFinally().actions(context -> deleteDuckViaDeleteRequest(runner, "${duckId}")));
        createDuckFromFileViaPostRequest(runner, DUCK_WITH_ACTIVE_WINGS_PATH);
        extractIdFromBody(runner);
        duckQuack(runner, "${duckId}", "3", "3");
        validateResponseAsJsonPath(runner, yellowDuckService, HttpStatus.OK, "$.sound", RESPONSE_MESSAGE_3);
    }

    @Test(description = "Проверка корректности количества звуков в теле ответа при при передаче трех повторов и нуля звуков")
    @CitrusTest
    public void checkMessageWithThreeRepetitionAndZeroQuacks(@Optional @CitrusResource TestCaseRunner runner) {
        runner.$(doFinally().actions(context -> deleteDuckViaSqlQuery(runner)));
        createDuckViaSqlQuery(runner);
        duckQuack(runner, "${duckId}", "3", "0");
        validateResponseAsJsonPath(runner, yellowDuckService, HttpStatus.OK, "$.sound", RESPONSE_MESSAGE_4);
    }
}