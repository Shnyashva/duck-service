package autotests.tests.duckcontrollertests;

import autotests.clients.DuckController;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

public class GetAllIdsTest extends DuckController {

    private final String DUCK = "{\n" +
            "  \"color\": \"blue\",\n" +
            "  \"height\": 10.0,\n" +
            "  \"material\": \"rubber\",\n" +
            "  \"sound\": \"quack\",\n" +
            "  \"wingsState\": \"ACTIVE\"\n" +
            "}";

    @Test(description = "Получение массива всех уток из базы данных")
    @CitrusTest
    public void getDucks(@Optional @CitrusResource TestCaseRunner runner) {
        runner.$(doFinally().actions(context -> deleteDuck(runner, "${duckId}")));
        createDuckFromString(runner, DUCK);
        extractIdFromBody(runner);
        getDucksIds(runner);
        validateResponseAsJsonPath(runner, yellowDuckService, HttpStatus.OK,
                "$.[*]", "@contains(${duckId})@");
    }
}