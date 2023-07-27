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

import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public abstract class BaseTest extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient yellowDuckService;

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
                                    String expectedJsonStringOrPathToJsonFile) {
        runner.$(http().client(url)
                .receive()
                .response(status)
                .message().type(MessageType.JSON)
                .body(expectedJsonStringOrPathToJsonFile)
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
    public void validateResponseFromFile(TestCaseRunner runner, String expectedPayload) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message().type(MessageType.JSON)
                .body(new ClassPathResource(expectedPayload))
        );
    }

    @Description("Response validation with POJO")
    public void validateResponseFromModel(TestCaseRunner runner, Object model) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message().type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(model, new ObjectMapper()))
        );
    }

    @Description("Delete duck")
    public void deleteDuck(TestCaseRunner runner, String id) {
        sendDeleteRequest(runner, yellowDuckService, "/api/duck/delete", "id", id);
    }

    @Description("Extract id from body")
    public void extractIdFromBody(TestCaseRunner runner) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response()
                .message().type(MessageType.JSON)
                .extract(fromBody().expression("$.id", "duckId"))
        );
    }
}