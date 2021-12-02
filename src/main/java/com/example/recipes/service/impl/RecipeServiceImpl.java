package com.example.recipes.service.impl;

import com.example.recipes.exception.NoSuchRecipeException;
import com.example.recipes.model.Recipe;
import com.example.recipes.repository.RecipeRepository;
import com.example.recipes.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
        return recipe;
    }

    @Override
    public List<Recipe> findRecipesByTitle(String title) {
        return recipeRepository.findRecipesByTitle(title).orElseThrow(() ->
                new NoSuchRecipeException("No such recipe"));

    }

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe findRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() ->
                new NoSuchRecipeException("No such recipe"));
    }

    @Override
    public List<Recipe> findRecipesByDescription(String description) {
        return recipeRepository.findRecipesByDescriptionContaining(description);
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }
}
