package com.bol.kalah.exception;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class KalahGameExceptionTest {

    @Test
    public void testKalahRuntimeException() {
        try {
            throw new KalahRuntimeException();
        } catch (KalahRuntimeException are) {
            assertNull(are.getMessage());
            assertNull(are.getCause());
        }
    }

    @Test
    public void testKalahRuntimeExceptionMessage() {
        try {
            throw new KalahRuntimeException("Internal Server Error");
        } catch (KalahRuntimeException are) {
            assertEquals("Internal Server Error", are.getMessage());
            assertNull(are.getCause());
        }
    }

    @Test
    public void testKalahRuntimeExceptionSource() {
        try {
            throw new KalahRuntimeException(new RuntimeException());
        } catch (KalahRuntimeException are) {
            assertEquals("java.lang.RuntimeException", are.getCause().getClass().getName());
        }
    }

    @Test
    public void testKalahRuntimeExceptionMessageSource() {
        try {
            throw new KalahRuntimeException("Internal Server Error", new RuntimeException());
        } catch (KalahRuntimeException are) {
            assertEquals("java.lang.RuntimeException", are.getCause().getClass().getName());
            assertEquals("Internal Server Error", are.getMessage());
        }
    }
}
