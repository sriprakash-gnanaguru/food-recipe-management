package com.food.recipe.manage.common;

import com.food.recipe.manage.adapter.in.http.domain.InputRequest;
import com.food.recipe.manage.adapter.out.db.dao.FoodReceipeRepository;
import com.food.recipe.manage.adapter.out.db.dto.FoodReceipe;
import com.food.recipe.manage.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.food.recipe.manage.adapter.out.db.dao.FoodReceipeSpecification.findbyIServings;
import static com.food.recipe.manage.adapter.out.db.dao.FoodReceipeSpecification.findbyIngredient;
import static com.food.recipe.manage.adapter.out.db.dao.FoodReceipeSpecification.findbyInstruction;
import static com.food.recipe.manage.adapter.out.db.dao.FoodReceipeSpecification.findbyIDish;
import static com.food.recipe.manage.common.transformer.ReceipeTransformer.translate;

@Service
public class FoodReceipeManagementService {
    @Autowired
    private  FoodReceipeRepository repository;
    @Transactional
    public FoodReceipe addReceipe(InputRequest request) throws ServiceException {
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
            if(receipe != null && receipe.getRecipeId() != null){
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

}

