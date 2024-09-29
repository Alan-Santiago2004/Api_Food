package com.project_food.cardapio.dtos;

import com.project_food.cardapio.models.enums.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
