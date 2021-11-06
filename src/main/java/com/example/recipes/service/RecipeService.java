package com.example.recipes.service;

import com.example.recipes.model.Recipe;

import java.util.List;

public interface RecipeService {
    Recipe createRecipe(Recipe recipe);

    List<Recipe> findRecipesByTitle(String title);

    List<Recipe> findAll();

    Recipe findRecipeById(Long id);
}
