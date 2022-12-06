package tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import reqres.User;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.*;

public class ReqresTest {
    
    private String HEADER_CONTENT_TYPE = "Content-type";
    private String HEADER_JSON = "application/json";


    @Test
    public void postCreateUserTest(){
        User user = User.builder().name("morpheus").job("leader").build();
        Response response =  given()
                .body(user)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_CREATED);
    }

    @Test
    public void getListUsers(){
        Response response = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void getSingleUser(){
        String expectedFirstName = "Janet";
        int expectedId = 2;
        given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("data.id", equalTo(expectedId))
                .body("data.first_name", equalTo(expectedFirstName));
    }
    @Test
    public void getSingleUserNotFound(){
        Response response = given()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_NOT_FOUND);
    }

    @Test
    public void getListResource(){
        Response response = given()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void getSingleResource(){
        Response response = given()
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void getSingleResourceNotFound(){
        Response response = given()
                .when()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_NOT_FOUND);
    }

    @Test
    public void putUpdate(){
        User user = User.builder().name("morpheus").job("zion resident").build();
        Response response = given()
                .header(HEADER_CONTENT_TYPE, HEADER_JSON)
                .body(user)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void patchUpdate(){
        User user = User.builder().name("morpheus").job("zion resident").build();
        Response response = given()
                .header(HEADER_CONTENT_TYPE, HEADER_JSON)
                .body(user)
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void delete(){
        Response response = given()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_NO_CONTENT);
    }

    @Test
    public void postRegisterSuccessful(){
        User user = User.builder().email("eve.holt@reqres.in").password("pistol").build();
        Response response = given()
                .header(HEADER_CONTENT_TYPE, HEADER_JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }
    @Test
    public void postRegisterUnsuccessful(){
        User user = User.builder().email("sydney@fife").build();
        Response response = given()
                .header(HEADER_CONTENT_TYPE, HEADER_JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_BAD_REQUEST);
    }

    @Test
    public void loginSuccessful(){
        User user = User.builder().email("eve.holt@reqres.in").password("cityslicka").build();
        Response response = given()
                .header(HEADER_CONTENT_TYPE, HEADER_JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void loginUnsuccessful(){
        User user = User.builder().email("peter@klaven").build();
        Response response = given()
                .header(HEADER_CONTENT_TYPE, HEADER_JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_BAD_REQUEST);
    }

    @Test
    public void getDelayedResponse(){
        Response response = given()
                .when()
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }
}
