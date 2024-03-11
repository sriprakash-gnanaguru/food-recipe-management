package com.food.recipe.manage.adapter.in.http;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Ingredient {

    private String ingredientId;

    private String name;
}
