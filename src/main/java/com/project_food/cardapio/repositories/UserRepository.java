package com.project_food.cardapio.repositories;

import com.project_food.cardapio.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel,String> {
    UserDetails findByLogin(String login);
}
