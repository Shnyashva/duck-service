package autotests.tests.duckcontrollertests;

import autotests.clients.DuckController;
import autotests.payloads.Duck;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static autotests.constants.FilePaths.*;

public class CreateDuckTest extends DuckController {

    private final Duck FIRST_DUCK = new Duck().color("green").height(10.0).material("wood")
            .sound("muamua").wingsState("ACTIVE");
    private final Duck SECOND_DUCK = new Duck().color("red").height(10.0).material("wood")
            .sound("muamua").wingsState("ACTIVE");
    private final Duck THIRD_DUCK = new Duck().color("blue").height(10.0).material("wood")
            .sound("muamua").wingsState("ACTIVE");
    private final Duck FOURTH_DUCK = new Duck().color("yellow").height(10.0).material("wood")
            .sound("muamua").wingsState("ACTIVE");
    private final Duck FIFTH_DUCK = new Duck().color("black").height(10.0).material("wood")
            .sound("muamua").wingsState("ACTIVE");
    private final String EMPTY_BODY_DUCK = "{}";

    @Test(dataProvider = "duckList", description = "Проверка корректности создания утки")
    @CitrusTest
    @CitrusParameters({"payload", "response", "runner"})
    public void successfulDuckCreate(Object payload, String response, @Optional @CitrusResource TestCaseRunner runner) {
        createDuckFromPayload(runner, payload);
        validateResponseFromFile(runner, response);
    }

    @DataProvider(name = "duckList")
    public Object[][] DuckProvider() {
        return new Object[][]{
                {FIRST_DUCK, TEST_DUCK_1_PATH, null},
                {SECOND_DUCK, TEST_DUCK_2_PATH, null},
                {THIRD_DUCK, TEST_DUCK_3_PATH, null},
                {FOURTH_DUCK, TEST_DUCK_4_PATH, null},
                {FIFTH_DUCK, TEST_DUCK_5_PATH, null}
        };
    }

    @Test(description = "Проверка, что поля объекта заполняются значениями по умолчанию")
    @CitrusTest
    public void createDuckWithEmptyBodyTest(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckFromString(runner, EMPTY_BODY_DUCK);
        validateResponseFromFile(runner, TEST_DUCK_EMPTY_BODY_PATH);
    }

    @Test(description = "Проверка корректности создания утки через sql-запрос")
    @CitrusTest
    public void createDuckUsingSqlQuery(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckViaSqlQuery(runner);
        validateDuckInDatabase(runner, "${duckId}", "orange", "100.0", "glass", "nya", "ACTIVE");
        deleteDuckViaSqlQuery(runner);
    }
}