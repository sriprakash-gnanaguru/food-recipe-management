package com.food.recipe.manage.common;

import com.food.recipe.manage.adapter.in.http.InputRequest;
import com.food.recipe.manage.adapter.out.db.FoodReceipe;
import com.food.recipe.manage.adapter.out.db.FoodReceipeRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
                receipe = repository.save(receipe);
            }
        }catch(Exception e){
            throw new ServiceException(String.valueOf(receipe.getRecipeId()),e);
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
        FoodReceipe receipe = null; receipe = repository.getFoodReceipeByRecipeId(Long.valueOf(receipeId));
        try{


        }catch(Exception e){
            throw new ServiceException(String.valueOf(receipe.getRecipeId()),e);
        }
        return translate(receipe);
    }

    private FoodReceipe translate(InputRequest request, FoodReceipe receipe){
        receipe = FoodReceipe.builder().recipeId(receipe.getRecipeId()).name(request.getName()).dish(request.getDish()).ingredient(request.getIngredient()).instruction(request.getInstruction())
                .servings(Integer.parseInt(request.getServings())).lastUpdatedBy(request.getUserId()).lastUpdatedTime(LocalDate.now()).build();
        return receipe;
    }

    private FoodReceipe translate(InputRequest request){
        FoodReceipe receipe = FoodReceipe.builder().name(request.getName()).dish(request.getDish()).ingredient(request.getIngredient()).instruction(request.getInstruction())
                .servings(Integer.parseInt(request.getServings())).lastUpdatedBy(request.getUserId()).lastUpdatedTime(LocalDate.now()).build();
        return receipe;
    }

    private InputRequest translate(FoodReceipe data){
        if(data != null && data.getRecipeId() != null){
            InputRequest request = InputRequest.builder().userId(data.getLastUpdatedBy()).servings(String.valueOf(data.getServings()))
                                        .ingredient(data.getIngredient()).instruction(data.getInstruction()).receipeId(String.valueOf(data.getRecipeId())).
                                        dish(data.getDish()).name(data.getName()).build();;
            return request;
        }else{
            return null;
        }
    }



}
