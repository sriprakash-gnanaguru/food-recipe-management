package com.food.recipe.manage.common.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ErrorHandlerTest  {

    @Test
    void testServiceException() {
        Exception exception = assertThrows(ServiceException.class, () -> {
            throw new ServiceException("Unable to connect the service",new Throwable());
        });
        String expectedMessage = "Unable to connect";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testBusinessException() {
        Exception exception = assertThrows(BusinessException.class, () -> {
            throw new BusinessException("Unable to process the data",new Throwable());
        });
        String expectedMessage = "Unable to process";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDatabaseException() {
        Exception exception = assertThrows(DatabaseException.class, () -> {
            throw new DatabaseException("Unable to connect the database",new Throwable());
        });
        String expectedMessage = "database";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
