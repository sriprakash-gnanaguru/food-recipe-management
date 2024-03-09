package com.food.recipe.manage.adapter.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodReceipeRepository extends JpaRepository<FoodReceipe,Long> {

    @Query(nativeQuery = true, value = "select * from FOOD_RECEIPE_MANAGEMENT where RECEIPE_ID = :receipeId")
    FoodReceipe getFoodReceipeByRecipeId(@Param("receipeId") Long receipeId);



}
