package com.viciustech.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetupTest {

    @Test
    public void validarQueOAmbienteEstaRodando() {
        System.out.println("O motor do JUnit está funcionando!");
        assertTrue(true);
    }
}