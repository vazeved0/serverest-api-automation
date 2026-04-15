package com.viciustech.tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Jornada Completa de Usuários - Serverest API")
public class UsuariosTest {

    private static final String BASE_URI = "https://serverest.dev";

    private static String idUsuarioGerado;
    private static String emailGerado;

    @Test
    @Order(1) // Passo 1: Criar
    @DisplayName("1. Deve criar um usuário com sucesso")
    public void deveCriarUsuario() {
        emailGerado = "qa." + System.currentTimeMillis() + "@viciustech.com.br";

        Map<String, Object> usuario = new HashMap<>();
        usuario.put("nome", "Vinicius Original");
        usuario.put("email", emailGerado);
        usuario.put("password", "senha123");
        usuario.put("administrador", "true");

        idUsuarioGerado = given()
            .baseUri(BASE_URI)
            .contentType(ContentType.JSON)
            .body(usuario)
            .when()
            .post("/usuarios")
            .then()
            .log().all()
            .statusCode(201)
            .body("message", is("Cadastro realizado com sucesso"))
            .extract().path("_id");
    }

    @Test
    @Order(2) // Passo 2: Buscar
    @DisplayName("2. Deve buscar o usuário recém-criado por ID")
    public void deveBuscarUsuarioPorId() {
        given()
            .baseUri(BASE_URI)
            .pathParam("idParam", idUsuarioGerado) // Busca pelo ID salvo no passo 1
            .when()
            .get("/usuarios/{idParam}") // Faz o GET na rota específica do usuário
            .then()
            .log().all()
            .statusCode(200)
            // Valida se os dados retornados batem com o que mandamos no POST
            .body("nome", is("Vinicius Original"))
            .body("email", is(emailGerado))
            .body("_id", is(idUsuarioGerado));
    }

    @Test
    @Order(3) // Passo 3: Alterar
    @DisplayName("3. Deve alterar o nome do usuário com sucesso")
    public void deveAlterarUsuario() {
        Map<String, Object> usuarioAlterado = new HashMap<>();
        usuarioAlterado.put("nome", "Vinicius Modificado pelo Teste");
        usuarioAlterado.put("email", emailGerado);
        usuarioAlterado.put("password", "novaSenha456");
        usuarioAlterado.put("administrador", "true");

        given()
            .baseUri(BASE_URI)
            .contentType(ContentType.JSON)
            .pathParam("idParam", idUsuarioGerado)
            .body(usuarioAlterado)
            .when()
            .put("/usuarios/{idParam}")
            .then()
            .log().all()
            .statusCode(200)
            .body("message", is("Registro alterado com sucesso"));
    }

    @Test
    @Order(4) // Passo 4: Excluir
    @DisplayName("4. Deve excluir o usuário ao final da jornada")
    public void deveExcluirUsuario() {
        given()
            .baseUri(BASE_URI)
            .pathParam("idParam", idUsuarioGerado)
            .when()
            .delete("/usuarios/{idParam}")
            .then()
            .log().all()
            .statusCode(200)
            .body("message", is("Registro excluído com sucesso"));
    }
}
