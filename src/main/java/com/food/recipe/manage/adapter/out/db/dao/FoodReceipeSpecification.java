package com.food.recipe.manage.adapter.out.db.dao;

import com.food.recipe.manage.adapter.out.db.dto.FoodReceipe;
import org.springframework.data.jpa.domain.Specification;

public class FoodReceipeSpecification  {


    public static Specification<FoodReceipe> findbyInstruction(String instruction) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("instruction"),"%" + instruction + "%" );
    }

    public static Specification<FoodReceipe> findbyIDish(String dish) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("dish"), dish);
    }

    public static Specification<FoodReceipe> findbyIngredient(String ingredient) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("recipeIngredients").get("name"), "%" + ingredient + "%");
    }

    public static Specification<FoodReceipe> findbyIServings(String servings) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("servings"), servings);
    }
}
