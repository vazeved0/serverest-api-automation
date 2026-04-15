package com.viciustech.tests;

import com.viciustech.models.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UsuariosTest extends BaseTest {


    @Test
    @DisplayName("Deve retornar a lista de usuários e validar as chaves do JSON")
    public void deveBuscarUsuariosComSucesso() {
        given()
            .when()
            .get("/usuarios")
            .then()
            .statusCode(200)
            // Validações do Corpo da Resposta (Payload)
            .body("quantidade", is(notNullValue()))
            .body("quantidade", is(greaterThanOrEqualTo(0)))
            .body("usuarios", is(notNullValue()))
            .body("usuarios[0]", hasKey("nome"))
            .body("usuarios[0]", hasKey("_id"));
    }

    @Test
    @DisplayName("Deve realizar a jornada completa de um usuário (CRUD) ponta a ponta")
    public void deveRealizarJornadaCompletaDoUsuario() {
        String emailDinamico = "qa." + System.currentTimeMillis() + "@viciustech.com.br";

        // Substituímos o HashMap pela nossa classe tipada!
        Usuario usuario = new Usuario(
            "Vinicius Original",
            emailDinamico,
            "senha123",
            "true"
        );

        // Passo 1: Criar (POST)
        String idExtraido = given()
            .body(usuario) // Olha como o given() ficou limpo. URI e ContentType já estão na BaseTest!
            .when()
            .post("/usuarios")
            .then()
            .statusCode(201)
            .body("message", is("Cadastro realizado com sucesso"))
            .extract().path("_id");

        System.out.println("Usuário criado com ID: " + idExtraido);

        // Passo 2: Buscar (GET)
        given()
            .pathParam("id", idExtraido)
            .when()
            .get("/usuarios/{id}")
            .then()
            .statusCode(200)
            .body("nome", is("Vinicius Original"))
            .body("email", is(emailDinamico));

        // Passo 3: Alterar (PUT)
        usuario.setNome("Vinicius Modificado pelo Teste"); // Reaproveitando o Map

        given()
            .pathParam("id", idExtraido)
            .body(usuario)
            .when()
            .put("/usuarios/{id}")
            .then()
            .statusCode(200)
            .body("message", is("Registro alterado com sucesso"));

        // Passo 4: Excluir (DELETE)
        given()
            .pathParam("id", idExtraido)
            .when()
            .delete("/usuarios/{id}")
            .then()
            .statusCode(200)
            .body("message", is("Registro excluído com sucesso"));
    }
}
