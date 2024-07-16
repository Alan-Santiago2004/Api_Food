package com.project_food.cardapio.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FoodRequestDto(@NotBlank String title,@NotBlank String image,@NotNull Double price) {
}
