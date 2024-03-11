package com.food.recipe.manage.adapter.in.http.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class InputRequest {

    private Receipe receipe;

    private String userId;

    private String msg;

}

