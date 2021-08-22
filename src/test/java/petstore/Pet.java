package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

public class Pet {

    String uri ="https://petstore.swagger.io/v2/pet"; // endereço da entidade pet


    public String lerJson(String caminhoJson) throws IOException {

        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }


    @Test(priority = 1)
    public void incluirPet() throws IOException {

        String jsonBody = lerJson("db/pet1.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()                                             //resposta
                .log().all()
                .statusCode(200)
                .body("name", is ("Vader"))
                .body("status", is ("available"))
                .body("category.name", is("AXS0102")) // is pq tem na estrutura sem colchete
                .body("tags.name", contains("tst"));
    }

    @Test (priority = 2)
    public void consultarPet(){
        String petId = "1994060132";

        String token = "AXS0102";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)  //coloca a uri mais um barra e o petid
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is ( "Vader"))
                .body("category.name", is ( "AXS0102"))
                .body("status", is ("available"))
        .extract()
                .path("category.name");
        System.out.println("O token é"+ token);


    }

}
