package tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.equalTo;

public class OnlinerTest {

    @Test
    public void getCurrencyUSDRateTest(){
        Response response =  given()
                .log().all()
                .when()
                .get("https://www.onliner.by/sdapi/kurs/api/bestrate?currency=USD&type=nbrb")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }
    @Test
    public void getCurrencyRUBRateTest(){
        given()
                .log().all()
                .when()
                .get("https://www.onliner.by/sdapi/kurs/api/bestrate?currency=RUB&type=nbrb")
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("amount", equalTo("3,9862"));
    }
    @Test
    public void getCurrencyEURRateTest(){
        given()
                .log().all()
                .when()
                .get("https://www.onliner.by/sdapi/kurs/api/bestrate?currency=EUR&type=nbrb")
                .then()
                .log().all()
                .statusCode(HTTP_OK);
    }

}
