package com.example.recipes.repository;

import com.example.recipes.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Optional<List<Recipe>> findRecipesByTitle(String title);

    List<Recipe> findRecipesByDescriptionContaining(String description);
}
