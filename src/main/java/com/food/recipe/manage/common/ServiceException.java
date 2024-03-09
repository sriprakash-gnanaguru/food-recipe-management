package com.food.recipe.manage.common;

import lombok.Builder;

import java.time.LocalDateTime;

public class ServiceException extends Exception {
    private LocalDateTime timestamp;
    private String errorMsg;
    private String receipeId;

    public ServiceException(String receipeId, Throwable cause) {
        this(receipeId,  null, cause);
    }

    @Builder
    public ServiceException(String receipeId, String errMsg, Throwable cause){
        super(receipeId,cause);
        this.timestamp = LocalDateTime.now();
    }

}
