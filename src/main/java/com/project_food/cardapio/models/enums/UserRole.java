package com.project_food.cardapio.models.enums;

import org.springframework.beans.factory.annotation.Autowired;

public enum UserRole {
    ADMIN("admin"),
    USER("user");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
