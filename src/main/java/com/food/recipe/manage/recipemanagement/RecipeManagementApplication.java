package com.food.recipe.manage.recipemanagement;

import com.food.recipe.manage.adapter.in.http.FoodReceipeManagementController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.food.recipe.manage"})
@EnableJpaRepositories("com.food.recipe.manage.adapter.out.db")
@EntityScan("com.food.recipe.manage.adapter.out.db")
public class RecipeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeManagementApplication.class, args);
	}

}
