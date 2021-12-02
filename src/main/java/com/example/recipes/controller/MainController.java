package com.example.recipes.controller;

import com.example.recipes.model.Ingredient;
import com.example.recipes.model.Recipe;
import com.example.recipes.model.User;
import com.example.recipes.service.IngredientService;
import com.example.recipes.service.impl.RecipeServiceImpl;
import com.example.recipes.service.impl.UserServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping("/recipes")
public class MainController {

    private final RecipeServiceImpl recipeService;
    private final UserServiceImpl userService;
    private final IngredientService ingredientService;

    public MainController(RecipeServiceImpl recipeService, UserServiceImpl userService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.userService = userService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public String findAll(Model model) {
//        initDb();
        List<Recipe> allRecipes = recipeService.findAll();
        model.addAttribute("recipes", allRecipes);
        return "recipes";
    }

    @GetMapping("/create")
    public String createRecipe() {
        return "createRecipe";
    }

    @PostMapping("/create")
    public String saveRecipe(@AuthenticationPrincipal User user,
                             @RequestParam String title, @RequestParam String description, @RequestParam String ingredients) {
        Recipe recipe = new Recipe(title, description, user, ingredients);
        recipeService.createRecipe(recipe);
        return "redirect:/";
    }

    @GetMapping("/recipe/{id}")
    public String getRecipeById(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findRecipeById(id);
        model.addAttribute("recipe", recipe);
        return "recipe";
    }

    @GetMapping("/recipe/{id}/favorite")
    public String addFavorite(@AuthenticationPrincipal User user1,
                              @PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findRecipeById(id);
        User user = userService.findByUsername(user1.getUsername());
        user.addFavoriteRecipe(recipe);
        userService.saveUser(user);
        model.addAttribute("recipe", recipe);
        return "recipe";
    }

    @GetMapping("/recipe/{id}/toShoppingList")
    public String addToShoppingList(@AuthenticationPrincipal User user1,
                              @PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findRecipeById(id);
        User user = userService.findByUsername(user1.getUsername());
        user.addIngredientsToBuy(recipe.getIngredients()[0]);
        userService.saveUser(user);
        model.addAttribute("recipe", recipe);
        return "recipe";
    }


    @PostMapping("/filter")
    public String filter(@RequestParam String title, Model model) {
        List<Recipe> recipes;
        if (title != null && !title.isEmpty()) {
            recipes = recipeService.findRecipesByTitle(title);
            if (recipes.isEmpty()) {
                recipes = recipeService.findRecipesByDescription(title);
            }
        } else {
            recipes = recipeService.findAll();
        }
        model.addAttribute("recipes", recipes);
        return "recipes";
    }
}
