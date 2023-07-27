package autotests.interfaces;

import com.consol.citrus.TestCaseRunner;

public interface ICreateDuck {

    void createDuckFromFile(TestCaseRunner runner, String filePath);

    void createDuckFromPayload(TestCaseRunner runner, Object model);

    void createDuckFromString(TestCaseRunner runner, String body);
}