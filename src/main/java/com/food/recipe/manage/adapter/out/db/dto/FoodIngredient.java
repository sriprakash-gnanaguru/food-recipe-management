package com.food.recipe.manage.adapter.out.db.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="FOOD_INGREDIENT")
public class FoodIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @jakarta.persistence.Id
    @Column(name="INGREDIENT_ID")
    private Long ingredientId;

    @Column(name="NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name="RECEIPE_ID", nullable=true)
    private FoodReceipe foodReceipe;

    @Column(name="LAST_UPDATED_TIME")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate lastUpdatedTime;

    @Column(name="LAST_UPDATED_BY")
    private String lastUpdatedBy;

}