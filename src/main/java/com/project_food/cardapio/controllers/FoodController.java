package com.project_food.cardapio.controllers;

import com.project_food.cardapio.dtos.FoodRequestDto;
import com.project_food.cardapio.models.FoodModel;
import com.project_food.cardapio.repositories.FoodRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/food")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FoodController {
    @Autowired
    private FoodRepository foodRepository;

    @Operation(description = "Operação para salvar um alimento no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação executada com sucesso"),
            @ApiResponse(responseCode = "417", description = "Erro de validação de dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }
    )
    @PostMapping
    public ResponseEntity<FoodModel> saveFood(@RequestBody @Valid FoodRequestDto foodRequestDto){
        FoodModel food = new FoodModel();
        BeanUtils.copyProperties(foodRequestDto,food);
        return ResponseEntity.status(HttpStatus.CREATED).body(foodRepository.save(food));
    }

    @Operation(description = "Operação para ler todos os alimentos do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação executada com sucesso"),
            @ApiResponse(responseCode = "417", description = "Erro de validação de dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }
    )
    @GetMapping
    public ResponseEntity<List<FoodModel>> getAllFoods(){
        List<FoodModel> foodList = foodRepository.findAll();
        if(!foodList.isEmpty()){
            for (FoodModel f : foodList){
                UUID id = f.getId();
                f.add(linkTo(methodOn(FoodController.class).getOneFood(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(foodList);
    }

    @Operation(description = "Operação para ler um alimento especifico do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação executada com sucesso"),
            @ApiResponse(responseCode = "417", description = "Erro de validação de dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneFood(@PathVariable(value = "id") UUID id){
        Optional<FoodModel> food = foodRepository.findById(id);
        if(food.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("food not found");
        }
        food.get().add(linkTo(methodOn(FoodController.class).getAllFoods()).withRel("Food List"));
        return ResponseEntity.status(HttpStatus.OK).body(food.get());
    }

    @Operation(description = "Operação para atualizar um alimento especifico do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação executada com sucesso"),
            @ApiResponse(responseCode = "417", description = "Erro de validação de dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateFood(@PathVariable(value = "id")UUID id,
                                                @RequestBody @Valid FoodRequestDto foodRequestDto){
        Optional<FoodModel> food = foodRepository.findById(id);
        if(food.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("food not found");
        }
        FoodModel foodUp = food.get();
        BeanUtils.copyProperties(foodRequestDto,foodUp);
        return ResponseEntity.status(HttpStatus.OK).body(foodRepository.save(foodUp));
    }

    @Operation(description = "Operação para deletar um alimento especifico do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação executada com sucesso"),
            @ApiResponse(responseCode = "417", description = "Erro de validação de dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id")UUID id){
        Optional<FoodModel> food = foodRepository.findById(id);
        if(food.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("food not found");
        }
        foodRepository.delete(food.get());
        return ResponseEntity.status(HttpStatus.OK).body("food deleted successfully");
    }
}
