package com.food.recipe.manage.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class BusinessException extends RuntimeException {
    private LocalDateTime timestamp;
    private String errorMsg;
    private String receipeId;

    public BusinessException(String receipeId, Throwable cause) {
         this(receipeId,  null, cause);
    }

    @Builder
    public BusinessException(String receipeId, String errMsg, Throwable cause){
        super(receipeId,cause);
        this.timestamp = LocalDateTime.now();
    }


}
