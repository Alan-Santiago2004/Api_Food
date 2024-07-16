package com.project_food.cardapio.repositories;

import com.project_food.cardapio.models.FoodModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FoodRepository extends JpaRepository<FoodModel,UUID> {
    Optional<FoodModel> findById(UUID id);
}
