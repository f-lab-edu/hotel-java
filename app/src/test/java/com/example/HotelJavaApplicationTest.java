package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HotelJavaApplicationTest {
    @Test
    public void appHasAGreeting() {
        HotelJavaApplication classUnderTest = new HotelJavaApplication();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }
}
