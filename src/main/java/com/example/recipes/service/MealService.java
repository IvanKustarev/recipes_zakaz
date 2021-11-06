package com.example.recipes.service;

import com.example.recipes.model.Meal;

import java.util.List;

public interface MealService {

    void createMeal(Meal meal);

    Meal findById(Long id);

    List<Meal> findAll();

    void save(Meal meal);

    void deleteById(Long mealId);
}
