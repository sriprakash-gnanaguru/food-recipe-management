package com.food.recipe.manage.adapter.in.http;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class InputRequest {

    private String receipeId;

    private String dish;

    private String ingredient;

    private String servings;

    private String instruction;

    private String name;

    private String userId;

    private String msg;

}
