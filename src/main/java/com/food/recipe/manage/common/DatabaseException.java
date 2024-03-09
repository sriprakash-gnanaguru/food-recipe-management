package com.food.recipe.manage.common;

import java.time.LocalDateTime;

public class DatabaseException extends Exception {
    private LocalDateTime timestamp;
    private String errorMsg;

    public DatabaseException(String errorMsg, Throwable cause) {
        super(errorMsg,cause);
        this.timestamp = LocalDateTime.now();
    }


}
