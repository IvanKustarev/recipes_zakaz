package com.example.recipes;

import com.example.recipes.model.Recipe;
import com.example.recipes.repository.RecipeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipesApplication implements Runnable {

	private final RecipeRepository recipeRepository;

	public RecipesApplication(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(RecipesApplication.class, args);
	}

	@Override
	public void run() {

	}
}
