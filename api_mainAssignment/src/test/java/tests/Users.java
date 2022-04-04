package tests;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.io.File;
import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class Users {

    @Test(priority = 1)
    public void userRegister() throws IOException {
        RestAssured.useRelaxedHTTPSValidation();

        for (int i = 1; i <= 5; i++) {

            ExcelData ed = new ExcelData();
            String nam = ed.getString(i, 0);
            String emai = ed.getString(i, 1);
            String pass = ed.getString(i, 2);
            int age = ed.getAge(i, 3);
            ReadingExcelData dt = new ReadingExcelData(nam, emai, pass, age);
            given().
                    body(dt).
                    contentType(ContentType.JSON).
                    when().
                    post("https://api-nodejs-todolist.herokuapp.com/user/register").
                    then().
                    log().body().
                    statusCode(HttpStatus.SC_CREATED);
        }
    }

    @Test(priority = 2)
    public void userLogin() throws IOException {
        RestAssured.useRelaxedHTTPSValidation();
        ExcelData ed = new ExcelData();
        String emai = ed.getString(1, 1);
        String pass = ed.getString(1, 2);
        ReadingExcelData dt = new ReadingExcelData(emai, pass);
        Response response = given().
                body(dt).
                contentType(ContentType.JSON).
                when().
                post("https://api-nodejs-todolist.herokuapp.com/user/login").
                then().
                log().body().
                statusCode(HttpStatus.SC_OK).extract().response();
        JSONObject jsonObject = new JSONObject(response.asString());
        Object obj = jsonObject.getJSONObject("user").get("email");
        assertThat(obj, is(emai));
        Object ObjToken = jsonObject.get("token");
        ExcelData excelData = new ExcelData();
        excelData.writeToken(ObjToken);
    }
}
