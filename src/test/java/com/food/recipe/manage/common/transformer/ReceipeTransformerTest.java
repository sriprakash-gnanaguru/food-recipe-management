package com.food.recipe.manage.common.transformer;

import com.food.recipe.manage.adapter.in.http.domain.Ingredient;
import com.food.recipe.manage.adapter.in.http.domain.InputRequest;
import com.food.recipe.manage.adapter.in.http.domain.Receipe;
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
public class ReceipeTransformerTest {

    @Test
    public void translate_Test1(){
        List<FoodReceipe> foodReceipes = new ArrayList<>();
        List<FoodIngredient> foodIngredients = getFoodIngredients();

        foodReceipes.add(FoodReceipe.builder().name("Paneer Butter Masala").dish("VEG").servings(2).instruction("Not spicy").recipeIngredients(foodIngredients).build());
        foodReceipes.add(FoodReceipe.builder().name("Aloo Masala").dish("VEG").servings(2).instruction("Not spicy").recipeIngredients(foodIngredients).build());
        foodReceipes.add(FoodReceipe.builder().name("Paneer Tikkie").dish("VEG").servings(2).instruction("Not spicy").recipeIngredients(foodIngredients).build());
        foodReceipes.add(FoodReceipe.builder().name("Butter Chicken").dish("NON-VEG").servings(2).instruction("Not spicy").recipeIngredients(foodIngredients).build());
        List<InputRequest> data = ReceipeTransformer.translate(foodReceipes);
        assertNotNull(data);
        assertEquals(4,data.size());
    }

    @Test
    public void translate_Test2(){
        InputRequest request = InputRequest.builder().build();
        List<Ingredient> foodIngredients = getIngredients();
        request.setReceipe(Receipe.builder().name("Paneer Butter Masala").dish("VEG").servings("2").instruction("Not spicy").ingredients(foodIngredients).build());
        FoodReceipe data = ReceipeTransformer.translate(request);
        assertNotNull(data);
        assertEquals(request.getReceipe().getName(), "Paneer Butter Masala");
        assertEquals(request.getReceipe().getDish(), "VEG");
    }

    @Test
    public void translate_Test3(){
        FoodReceipe data = FoodReceipe.builder().recipeId(1L).name("Paneer Butter Masala").dish("VEG").servings(2).instruction("Not spicy").lastUpdatedBy("User1").recipeIngredients(getFoodIngredients()).build();
        InputRequest request = ReceipeTransformer.translate(data);
        assertNotNull(request);
        assertEquals(request.getReceipe().getName(), "Paneer Butter Masala");
        assertEquals(request.getReceipe().getDish(), "VEG");
    }

    @Test
    public void translate_Test4(){
        Receipe receipe = Receipe.builder().receipeId("1").name("Paneer Butter Masala").dish("VEG").servings("2").instruction("Not spicy").ingredients(getIngredients()).build();
        InputRequest request = InputRequest.builder().receipe(receipe).build();
        FoodReceipe data  =ReceipeTransformer.translate(request);
        assertNotNull(request);
        assertEquals(request.getReceipe().getName(), "Paneer Butter Masala");
        assertEquals(request.getReceipe().getDish(), "VEG");
    }

    @Test
    public void translate_Test5(){
        Receipe receipe = Receipe.builder().name("Paneer Butter Masala").dish("VEG").servings("2").instruction("Not spicy").ingredients(getIngredients()).build();
        InputRequest request = InputRequest.builder().receipe(receipe).build();
        FoodReceipe data = FoodReceipe.builder().name("Butter Chicken").dish("NON-VEG").build();
        data = ReceipeTransformer.translate(request,data);

        assertNotNull(data.getRecipeIngredients());
        assertEquals(data.getRecipeIngredients().size(), 4);
        assertEquals(data.getName(), "Paneer Butter Masala");
        assertEquals(data.getDish(), "VEG");
    }

    private List<FoodIngredient> getFoodIngredients(){
        List<FoodIngredient> foodIngredients = new ArrayList<>();
        foodIngredients.add(FoodIngredient.builder().name("Garlic").ingredientId(1L).build());
        foodIngredients.add(FoodIngredient.builder().name("Onion").ingredientId(2L).build());
        foodIngredients.add(FoodIngredient.builder().name("Paneer").ingredientId(3L).build());
        foodIngredients.add(FoodIngredient.builder().name("Butter").ingredientId(4L).build());
        return foodIngredients;
    }

    private List<Ingredient> getIngredients(){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(Ingredient.builder().name("Garlic").ingredientId("1").build());
        ingredients.add(Ingredient.builder().name("Onion").ingredientId("2").build());
        ingredients.add(Ingredient.builder().name("Paneer").ingredientId("3").build());
        ingredients.add(Ingredient.builder().name("Butter").ingredientId("4").build());
        return  ingredients;
    }


}
