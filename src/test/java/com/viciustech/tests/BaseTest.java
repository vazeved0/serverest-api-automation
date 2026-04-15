package com.viciustech.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    @BeforeAll
    public static void setup() {
        // 1. Define a URI base para todos os testes que herdarem esta classe
        RestAssured.baseURI = "https://serverest.dev";

        // 2. Define que o padrão de envio de dados será sempre JSON
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();

        // 3. Configura o RestAssured para logar no console
        // a requisição e a resposta SOMENTE se o teste falhar. Mantém o log limpo em caso de sucesso.
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
