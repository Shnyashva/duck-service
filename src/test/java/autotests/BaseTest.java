package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import static autotests.constants.SqlQueries.SELECT_LAST_DUCK_ID_FROM_DATABASE_QUERY;
import static autotests.constants.SqlQueries.SELECT_NUMBER_OF_DUCKS_FROM_TABLE;
import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public abstract class BaseTest extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient yellowDuckService;

    @Autowired
    protected SingleConnectionDataSource ducksDataBase;

    @Description("Base get request method with 1 param")
    protected void sendGetRequest(TestCaseRunner runner, HttpClient url, String path,
                                  String queryParamName, String queryValue) {
        runner.$(http().client(url)
                .send()
                .get(path)
                .queryParam(queryParamName, queryValue)
        );
    }

    @Description("Base get request method without any params")
    protected void sendGetRequest(TestCaseRunner runner, HttpClient url, String path) {
        runner.$(http().client(url)
                .send()
                .get(path)
        );
    }

    @Description("Sending post request with data from file")
    protected void sendPostRequestFromFile(TestCaseRunner runner, HttpClient url, String path, String filePath) {
        runner.$(http().client(url)
                .send()
                .post(path)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ClassPathResource(filePath))
        );
    }

    @Description("Sending post request as model")
    protected void sendPostRequestAsPayload(TestCaseRunner runner, HttpClient url, String path, Object model) {
        runner.$(http().client(url)
                .send()
                .post(path)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(model, new ObjectMapper()))
        );
    }

    @Description("Sending post request as string")
    protected void sendPostRequestAsString(TestCaseRunner runner, HttpClient url, String path, String body) {
        runner.$(http().client(url)
                .send()
                .post(path)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
        );
    }

    @Description("Base delete request method")
    protected void sendDeleteRequest(TestCaseRunner runner, HttpClient url, String path, String paramName, String id) {
        runner.$(http().client(url)
                .send()
                .delete(path)
                .queryParam(paramName, id)
        );
    }

    @Description("Response validation with body as string")
    protected void validateResponse(TestCaseRunner runner, HttpClient url, HttpStatus status,
                                    String JsonString) {
        runner.$(http().client(url)
                .receive()
                .response(status)
                .message().type(MessageType.JSON)
                .body(JsonString)
        );
    }

    @Description("Response status code validation")
    protected void validateResponse(TestCaseRunner runner, HttpClient url, HttpStatus status) {
        runner.$(http().client(url)
                .receive()
                .response(status)
                .message().type(MessageType.JSON)
        );
    }

    @Description("Response validation as JsonPath")
    protected void validateResponseAsJsonPath(TestCaseRunner runner, HttpClient url, HttpStatus status,
                                              String expression, String message) {
        runner.$(http().client(url)
                .receive()
                .response(status)
                .message().type(MessageType.JSON)
                .validate(jsonPath().expression(expression, message))
        );
    }

    @Description("Response validation with JSON file")
    protected void validateResponseFromFile(TestCaseRunner runner, String filePath) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message().type(MessageType.JSON)
                .body(new ClassPathResource(filePath))
        );
    }

    @Description("Response validation with POJO")
    protected void validateResponseFromModel(TestCaseRunner runner, Object model) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message().type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(model, new ObjectMapper()))
        );
    }

    @Description("Extract id from body")
    protected void extractIdFromBody(TestCaseRunner runner) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response()
                .message().type(MessageType.JSON)
                .extract(fromBody().expression("$.id", "duckId"))
        );
    }

    @Description("Update dataBase")
    protected void updateDataBase(TestCaseRunner runner, SingleConnectionDataSource dataBase, String sqlQuery) {
        runner.$(sql(dataBase).statement(sqlQuery));
    }

    @Description("Read dataBase")
    protected void readDataBase(TestCaseRunner runner, SingleConnectionDataSource dataBase, String sqlQuery) {
        runner.$(query(dataBase).statement(sqlQuery));
    }

    @Description("Duck properties validation in database via id")
    protected void validateDuckInDatabase(TestCaseRunner runner, String id, String color, String height,
                                          String material, String sound, String wingsState) {
        runner.$(query(ducksDataBase).statement("SELECT * FROM DUCK WHERE ID=" + id)
                .validate("COLOR", color)
                .validate("HEIGHT", height)
                .validate("MATERIAL", material)
                .validate("SOUND", sound)
                .validate("WINGS_STATE", wingsState));
    }

    @Description("Extract value from database")
    protected void extractVariableFromColumn(TestCaseRunner runner, String SqlQuery, String columnName, String variableName) {
        runner.$(query(ducksDataBase)
                .statement(SqlQuery)
                .extract(columnName, variableName)
        );
    }

    @Description("Method for setting the duck ID, depending on whether the table is empty or not.")
    protected void setDuckIdVariable(TestCaseRunner runner) {
        extractVariableFromColumn(runner, SELECT_NUMBER_OF_DUCKS_FROM_TABLE, "duckcount", "duckCount");
        runner.$(context -> {
            int duckCount = context.getVariable("duckCount", Integer.class);
            if (duckCount == 0) {
                runner.variable("duckId", "1");
            } else {
                extractVariableFromColumn(runner, SELECT_LAST_DUCK_ID_FROM_DATABASE_QUERY, "id", "lastDuckId");
                int lastDuckId = context.getVariable("lastDuckId", Integer.class);
                runner.variable("duckId", Integer.toString(lastDuckId + 1));
            }
        });
    }
}