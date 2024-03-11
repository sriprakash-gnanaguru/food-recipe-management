package com.food.recipe.manage.adapter.in.http;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Receipe {

    private String receipeId;

    private String dish;

    private List<Ingredient> ingredients;

    private String servings;

    private String instruction;

    private String name;
}
