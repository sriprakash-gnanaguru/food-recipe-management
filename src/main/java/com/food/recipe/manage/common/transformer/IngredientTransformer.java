package com.food.recipe.manage.common.transformer;

import com.food.recipe.manage.adapter.in.http.domain.Ingredient;
import com.food.recipe.manage.adapter.out.db.dto.FoodIngredient;
import com.food.recipe.manage.adapter.out.db.dto.FoodReceipe;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IngredientTransformer {
    public static List<Ingredient> convertIngredientIntoDomain(List<FoodIngredient> foodIngredients){
        List<Ingredient> ingredients = new ArrayList<>();
        foodIngredients.forEach(foodIngredient->{
            Ingredient ingredient = Ingredient.builder().ingredientId(String.valueOf(foodIngredient.getIngredientId())).name(foodIngredient.getName()).build();
            ingredients.add(ingredient);
        });
        return ingredients;
    }

    public static List<FoodIngredient> convertIngredientIntoDto(List<Ingredient> ingredients, FoodReceipe receipe, String userId){
        List<FoodIngredient> foodIngredients = new ArrayList<>();
        if(receipe.getRecipeIngredients() !=null){
            receipe.getRecipeIngredients().clear();
        };
        ingredients.forEach(ingredient->{
            FoodIngredient foodIngredient = FoodIngredient.builder().foodReceipe(receipe).lastUpdatedBy(userId).lastUpdatedTime(LocalDate.now()).name(ingredient.getName()).build();
            foodIngredients.add(foodIngredient);
        });
        return foodIngredients;
    }
}
