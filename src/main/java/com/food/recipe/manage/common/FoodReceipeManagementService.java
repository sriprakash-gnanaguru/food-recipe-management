package com.food.recipe.manage.common;

import com.food.recipe.manage.adapter.in.http.Ingredient;
import com.food.recipe.manage.adapter.in.http.InputRequest;
import com.food.recipe.manage.adapter.in.http.Receipe;
import com.food.recipe.manage.adapter.out.db.FoodIngredient;
import com.food.recipe.manage.adapter.out.db.FoodReceipe;
import com.food.recipe.manage.adapter.out.db.FoodReceipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.food.recipe.manage.adapter.out.db.FoodReceipeSpecification.findbyInstruction;
import static com.food.recipe.manage.adapter.out.db.FoodReceipeSpecification.findbyIDish;
import static com.food.recipe.manage.adapter.out.db.FoodReceipeSpecification.findbyIngredient;
import static com.food.recipe.manage.adapter.out.db.FoodReceipeSpecification.findbyIServings;



@Service
public class FoodReceipeManagementService {
    @Autowired
    private  FoodReceipeRepository repository;
    @Transactional
    public FoodReceipe addReceipe(InputRequest request) throws ServiceException{
        FoodReceipe receipe = null;
        try{
             receipe = repository.save(translate(request));
        }catch(Exception e){
            throw new ServiceException(String.valueOf(receipe.getRecipeId()),e);
        }
        return receipe;
    }

    @Transactional
    public FoodReceipe updateReceipe(InputRequest request,String receipeId) throws ServiceException{
        FoodReceipe receipe = null;
        try{
            receipe = repository.getFoodReceipeByRecipeId(Long.valueOf(receipeId));
            if(receipe != null || receipe.getRecipeId() != null){
                receipe = translate(request,receipe);
                receipe.setRecipeId(Long.valueOf(receipeId));
                receipe = repository.save(receipe);
            }
        }catch(Exception e){
            throw new ServiceException(receipeId,e);
        }
        return receipe;
    }

    @Transactional
    public void deleteReceipe(String receipeId) throws ServiceException{
        FoodReceipe receipe = null;
        try{
            repository.deleteById(Long.valueOf(receipeId));
        }catch(Exception e){
            throw new ServiceException(String.valueOf(receipe.getRecipeId()),e);
        }
    }

    @Transactional
    public InputRequest getReceipe(String receipeId) throws ServiceException{
        FoodReceipe receipe = null;
        try{
            receipe = repository.getFoodReceipeByRecipeId(Long.valueOf(receipeId));
        }catch(Exception e){
            throw new ServiceException(String.valueOf(receipe.getRecipeId()),e);
        }
        return translate(receipe);
    }

    @Transactional
    public List<InputRequest> getReceipes(String dish,String ingredient, String servings, String instruction) throws ServiceException{
        List<FoodReceipe> receipes = new ArrayList<>();
        Specification<FoodReceipe> spec = Specification.where(null);
        try{
            if(instruction != null && !instruction.isEmpty()){
                spec = spec.and(findbyInstruction(instruction));
            }
            if(dish != null && !dish.isEmpty() ){
                spec = spec.and(findbyIDish(dish));
            }
            if(servings != null && !servings.isEmpty() ){
                spec = spec.and(findbyIServings(servings));
            }
            if(ingredient != null && !ingredient.isEmpty() ){
                spec = spec.and(findbyIngredient(ingredient));
            }
            receipes = repository.findAll(spec);
        }catch(Exception e){
            throw new ServiceException(e);
        }
        return translate(receipes);
    }

    private List<InputRequest> translate(List<FoodReceipe> receipes){
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


    private FoodReceipe translate(InputRequest request, FoodReceipe receipe){
        receipe = FoodReceipe.builder().name(request.getReceipe().getName()).dish(request.getReceipe().getDish()).
                recipeIngredients(convertIngredientIntoDto(request.getReceipe().getIngredients(),receipe, request.getUserId())).
                instruction(request.getReceipe().getInstruction())
                .servings(Integer.parseInt(request.getReceipe().getServings())).lastUpdatedBy(request.getUserId()).lastUpdatedTime(LocalDate.now()).build();
        return receipe;
    }

    private List<FoodReceipe> convertReceipeIntoDto(List<Receipe> receipes,String userId){
        List<FoodReceipe> foodReceipes = new ArrayList<>();
        receipes.forEach(receipe->{
            FoodReceipe foodReceipe = FoodReceipe.builder().recipeId(Long.valueOf(receipe.getReceipeId())).name(receipe.getName())
                    .dish(receipe.getDish()).instruction(receipe.getInstruction()).build();
            foodReceipe.setRecipeIngredients(convertIngredientIntoDto(receipe.getIngredients(),foodReceipe, userId));
            foodReceipes.add(foodReceipe);
        });
        return foodReceipes;
    }

    private List<Receipe> convertReceipeIntoDomain(List<FoodReceipe> foodReceipes){
        List<Receipe> receipes = new ArrayList<>();
        foodReceipes.forEach(foodReceipe->{
            Receipe receipe = Receipe.builder().receipeId(String.valueOf(foodReceipe.getRecipeId())).name(foodReceipe.getName())
                    .dish(foodReceipe.getDish()).instruction(foodReceipe.getInstruction()).ingredients(convertIngredientIntoDomain(foodReceipe.getRecipeIngredients())).build();
            receipes.add(receipe);
        });
        return receipes;
    }

    private List<FoodIngredient> convertIngredientIntoDto(List<Ingredient> ingredients,FoodReceipe receipe,String userId){
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

    private List<Ingredient> convertIngredientIntoDomain(List<FoodIngredient> foodIngredients){
        List<Ingredient> ingredients = new ArrayList<>();
        foodIngredients.forEach(foodIngredient->{
            Ingredient ingredient = Ingredient.builder().ingredientId(String.valueOf(foodIngredient.getIngredientId())).name(foodIngredient.getName()).build();
            ingredients.add(ingredient);
        });
        return ingredients;
    }

    private FoodReceipe translate(InputRequest request){
        FoodReceipe receipe = FoodReceipe.builder().name(request.getReceipe().getName()).dish(request.getReceipe().getDish()).instruction(request.getReceipe().getInstruction())
                .servings(Integer.parseInt(request.getReceipe().getServings())).lastUpdatedBy(request.getUserId()).lastUpdatedTime(LocalDate.now()).build();
        receipe.setRecipeIngredients(convertIngredientIntoDto(request.getReceipe().getIngredients(),receipe, request.getUserId()));
        return receipe;
    }

    private InputRequest translate(FoodReceipe data){
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
