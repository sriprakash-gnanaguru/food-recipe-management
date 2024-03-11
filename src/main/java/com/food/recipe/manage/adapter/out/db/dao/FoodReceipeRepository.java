package com.food.recipe.manage.adapter.out.db.dao;

import com.food.recipe.manage.adapter.out.db.dto.FoodReceipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodReceipeRepository extends JpaRepository<FoodReceipe,Long>, JpaSpecificationExecutor<FoodReceipe> {

    @Query(nativeQuery = true, value = "select * from FOOD_RECEIPE where RECEIPE_ID = :receipeId")
    FoodReceipe getFoodReceipeByRecipeId(@Param("receipeId") Long receipeId);


}
