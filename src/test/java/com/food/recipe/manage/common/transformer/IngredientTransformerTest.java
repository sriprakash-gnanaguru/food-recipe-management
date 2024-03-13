package com.food.recipe.manage.common.transformer;

import com.food.recipe.manage.adapter.in.http.domain.Ingredient;
import com.food.recipe.manage.adapter.out.db.dto.FoodIngredient;
import com.food.recipe.manage.adapter.out.db.dto.FoodReceipe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class IngredientTransformerTest {

    @Test
    public void convertIngredientIntoDomainTest(){
        List<FoodIngredient> foodIngredients = new ArrayList<>();
        foodIngredients.add(FoodIngredient.builder().name("Garlic").ingredientId(1L).build());
        foodIngredients.add(FoodIngredient.builder().name("Onion").ingredientId(2L).build());
        foodIngredients.add(FoodIngredient.builder().name("Paneer").ingredientId(3L).build());
        foodIngredients.add(FoodIngredient.builder().name("Butter").ingredientId(4L).build());
        List<Ingredient> ingredients = IngredientTransformer.convertIngredientIntoDomain(foodIngredients);
        assertNotNull(ingredients);
        assertEquals(4,ingredients.size());
    }

    @Test
    public void convertIngredientIntoDtoTest(){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(Ingredient.builder().name("Garlic").ingredientId("1").build());
        ingredients.add(Ingredient.builder().name("Onion").ingredientId("2").build());
        ingredients.add(Ingredient.builder().name("Paneer").ingredientId("3").build());
        ingredients.add(Ingredient.builder().name("Butter").ingredientId("4").build());
        FoodReceipe foodReceipe = FoodReceipe.builder().name("Paneer Butter Masala").instruction("without oven").servings(3).dish("VEG").build();
        List<FoodIngredient> foodIngredients = IngredientTransformer.convertIngredientIntoDto(ingredients,foodReceipe,"User1");
        assertNotNull(foodIngredients);
        assertEquals(4,foodIngredients.size());
    }

}
