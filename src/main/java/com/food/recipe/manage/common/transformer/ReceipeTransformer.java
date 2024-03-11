package com.food.recipe.manage.common.transformer;

import com.food.recipe.manage.adapter.in.http.domain.InputRequest;
import com.food.recipe.manage.adapter.in.http.domain.Receipe;
import com.food.recipe.manage.adapter.out.db.dto.FoodReceipe;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.food.recipe.manage.common.transformer.IngredientTransformer.convertIngredientIntoDomain;
import static com.food.recipe.manage.common.transformer.IngredientTransformer.convertIngredientIntoDto;

public class ReceipeTransformer {
    public static List<InputRequest> translate(List<FoodReceipe> receipes){
        List<InputRequest> response = new ArrayList<>();
        if(receipes != null && !receipes.isEmpty()){
            receipes.forEach(data ->{
                InputRequest request = InputRequest.builder().userId(data.getLastUpdatedBy()).receipe(Receipe.builder()
                        .name(data.getName()).instruction(data.getInstruction()).ingredients(convertIngredientIntoDomain(data.getRecipeIngredients()))
                        .dish(data.getDish()).servings(String.valueOf(data.getServings())).receipeId(String.valueOf(data.getRecipeId()))
                        .build()).build();
                response.add(request);
            });
        }
        return response;
    }
    public static FoodReceipe translate(InputRequest request, FoodReceipe receipe){
        receipe = FoodReceipe.builder().name(request.getReceipe().getName()).dish(request.getReceipe().getDish()).
                recipeIngredients(convertIngredientIntoDto(request.getReceipe().getIngredients(),receipe, request.getUserId())).
                instruction(request.getReceipe().getInstruction())
                .servings(Integer.parseInt(request.getReceipe().getServings())).lastUpdatedBy(request.getUserId()).lastUpdatedTime(LocalDate.now()).build();
        return receipe;
    }

    public static FoodReceipe translate(InputRequest request){
        FoodReceipe receipe = FoodReceipe.builder().name(request.getReceipe().getName()).dish(request.getReceipe().getDish()).instruction(request.getReceipe().getInstruction())
                .servings(Integer.parseInt(request.getReceipe().getServings())).lastUpdatedBy(request.getUserId()).lastUpdatedTime(LocalDate.now()).build();
        receipe.setRecipeIngredients(convertIngredientIntoDto(request.getReceipe().getIngredients(),receipe, request.getUserId()));
        return receipe;
    }

    public static InputRequest translate(FoodReceipe data){
        if(data != null && data.getRecipeId() != null){
            InputRequest request = InputRequest.builder().userId(data.getLastUpdatedBy()).
                    receipe(Receipe.builder().name(data.getName()).ingredients(convertIngredientIntoDomain(data.getRecipeIngredients())).instruction(data.getInstruction()).dish(data.getDish())
                            .receipeId(String.valueOf(data.getRecipeId())).servings(String.valueOf(data.getServings())).build()).build();
            return request;
        }else{
            return null;
        }
    }
}