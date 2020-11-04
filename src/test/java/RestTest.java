import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

public class RestTest {


    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void sedan_test(){
        RestAssured.with()
                .body("{\"type\":\"sedan\"}")
                .contentType("application/json")
                .request("POST","/car").then().statusCode(200);
    }

    @Test
    public void cabrio_test(){
        RestAssured.with()
                .body("{\"type\":\"cabrio\"}")
                .contentType("application/json")
                .request("POST","/car").then().statusCode(200);
    }

    @Test
    public void hatchback_test(){
        RestAssured.with()
                .body("{\"type\":\"hatchback\"}")
                .contentType("application/json")
                .request("POST","/car").then().statusCode(200);
    }

    @Test
    public void hatchback_upper_case_test(){
        RestAssured.with()
                .body("{\"type\":\"HATCHBACK\"}")
                .contentType("application/json")
                .request("POST","/car").then().statusCode(200);
    }

    @Test
    public void cabrio_upper_case_test(){
        RestAssured.with()
                .body("{\"type\":\"CABRIO\"}")
                .contentType("application/json")
                .request("POST","/car").then().statusCode(200);
    }

    @Test
    public void sedan_upper_case_test(){
        RestAssured.with()
                .body("{\"type\":\"SEDAN\"}")
                .contentType("application/json")
                .request("POST","/car").then().statusCode(200);
    }

    @Test
    public void unexpected_type_test(){
        RestAssured.with()
                .body("{\"type\":\"asdasd\"}")
                .contentType("application/json")
                .request("POST","/car").then().statusCode(500);
    }

    @Test
    public void type_is_required_test(){
        RestAssured.with()
                .contentType("application/json")
                .request("POST","/car").then().statusCode(422);
    }
}
