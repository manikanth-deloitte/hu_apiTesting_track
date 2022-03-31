
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.io.File;
import static  io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RestAssured {

    @Test
    public void test_get_call(){
        RestAssured.useRelaxedHTTPSValidation();
        Response responce = (Response) given().
                baseUri("https://jsonplaceholder.typicode.com").
                header("Content-Type", "application/json").

                when().
                get("/posts").

                then().
                statusCode(200);
    }

    @Test
    public void test_put_call(){
        File jsonFile = new File("src\\test\\resources\\putcall.json");
        given().
                baseUri("https://reqres.in/api").
                header("Content-Type", "application/json").
                body(jsonFile).
                when().
                put("/users").
                then().
                statusCode(200).
                body("name",equalTo("Arun")).
                body("job",equalTo("Manager"));
    }

}

