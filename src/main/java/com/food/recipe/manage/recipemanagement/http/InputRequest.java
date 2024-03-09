package com.food.recipe.manage.recipemanagement.http;

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

}

