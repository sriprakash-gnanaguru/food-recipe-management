package com.food.recipe.manage.common.transformer;

import com.food.recipe.manage.adapter.in.http.domain.Ingredient;
import com.food.recipe.manage.adapter.in.http.domain.InputRequest;
import com.food.recipe.manage.adapter.in.http.domain.Receipe;
import com.food.recipe.manage.adapter.out.db.dao.FoodReceipeRepository;
import com.food.recipe.manage.adapter.out.db.dto.FoodIngredient;
import com.food.recipe.manage.adapter.out.db.dto.FoodReceipe;
import com.food.recipe.manage.common.FoodReceipeManagementService;
import com.food.recipe.manage.common.exception.ServiceException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FoodReceipeManagmentServiceTest {
    @Mock
    FoodReceipeRepository  repository;
    @InjectMocks
    FoodReceipeManagementService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addReceipeTest() throws ServiceException {
        when(repository.save(getFoodRecipe())).thenReturn(FoodReceipe.builder().build());
        assertNull(service.addReceipe(getInput()));
    }

    @Test
    public void deleteReceipeTest() throws ServiceException {
        service.deleteReceipe("1");
        verify(repository).deleteById(any());
    }

    @Test
    public void getReceipeTest() throws ServiceException {
        when(repository.getFoodReceipeByRecipeId(1L)).thenReturn(FoodReceipe.builder().build());
        assertNull(service.getReceipe("1"));
    }

    @Test
    public void updateReceipeTest() throws ServiceException {
        when(repository.save(getFoodRecipe())).thenReturn(FoodReceipe.builder().recipeId(1L).build());
        assertNull(service.updateReceipe(getInput(),"1"));
    }

    private FoodReceipe getFoodRecipe() {
        List<FoodIngredient> foodIngredients = new ArrayList<>();
        foodIngredients.add(FoodIngredient.builder().name("Garlic").ingredientId(1L).build());
        foodIngredients.add(FoodIngredient.builder().name("Onion").ingredientId(2L).build());
        foodIngredients.add(FoodIngredient.builder().name("Paneer").ingredientId(3L).build());
        foodIngredients.add(FoodIngredient.builder().name("Butter").ingredientId(4L).build());
        FoodReceipe data = FoodReceipe.builder().recipeId(1L).name("Paneer Butter Masala").dish("VEG").servings(2).instruction("Not spicy").lastUpdatedBy("User1").recipeIngredients(foodIngredients).build();
        return data;
    }

    private InputRequest getInput() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(Ingredient.builder().name("Garlic").ingredientId("1").build());
        ingredients.add(Ingredient.builder().name("Onion").ingredientId("2").build());
        ingredients.add(Ingredient.builder().name("Paneer").ingredientId("3").build());
        ingredients.add(Ingredient.builder().name("Butter").ingredientId("4").build());
        Receipe foodReceipe = Receipe.builder().receipeId("1").name("Paneer Butter Masala").instruction("without oven").servings("3").dish("VEG").ingredients(ingredients).build();
        InputRequest inputRequest = InputRequest.builder().userId("user1").receipe(foodReceipe).build();
        return inputRequest;
    }

}
