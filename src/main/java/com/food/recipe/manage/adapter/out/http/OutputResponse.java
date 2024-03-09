package com.food.recipe.manage.adapter.out.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutputResponse {
    private String status;
    private String receipeId;
    private String msg;
    private String errorDescr;


}
