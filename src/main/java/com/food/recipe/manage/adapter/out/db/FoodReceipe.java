package com.food.recipe.manage.adapter.out.db;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="FOOD_RECEIPE")
public class FoodReceipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @jakarta.persistence.Id
    @Column(name="RECEIPE_ID")
    private Long recipeId;

    @Column(name="NAME")
    private String name;

    @Column(name="DISH")
    private String dish;

    @Column(name="NO_OF_SERVINGS")
    private Integer servings;

    @OneToMany(mappedBy = "foodReceipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodIngredient> recipeIngredients = new ArrayList<>();

    @Column(name="INSTRUCTION")
    private String instruction;

    @Column(name="LAST_UPDATED_TIME")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate lastUpdatedTime;

    @Column(name="LAST_UPDATED_BY")
    private String lastUpdatedBy;

}