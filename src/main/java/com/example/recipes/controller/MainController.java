package com.example.recipes.controller;

import com.example.recipes.model.Meal;
import com.example.recipes.model.Recipe;
import com.example.recipes.model.User;
import com.example.recipes.service.impl.MealServiceImpl;
import com.example.recipes.service.impl.RecipeServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/recipes")
public class MainController {

    private final RecipeServiceImpl recipeService;


    public MainController(RecipeServiceImpl recipeService) {
        this.recipeService = recipeService;
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
            @RequestParam String title, @RequestParam String description) {
        Recipe recipe = new Recipe(title, description, user);
        recipeService.createRecipe(recipe);
        return "redirect:/recipes";
    }

//    public void initDb() {
//        recipeService.createRecipe(new Recipe(1L, "Cereal", "It's cereal recipe"));
//        recipeService.createRecipe(new Recipe(2L,"Potato", "It's potato recipe"));
//        recipeService.createRecipe(new Recipe(3L,"Soup", "It's soup recipe"));
//        System.out.println("it's flushed");
//    }

    @GetMapping("/{id}")
    public String getRecipeById(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findRecipeById(id);
        System.out.println(recipe);
        model.addAttribute("recipe", recipe);
        return "recipe";
    }

//    @GetMapping("/{title}")
//    public String getRecipeByTitle(@PathVariable String title, Model model) {
//        Recipe recipe = recipeService.findRecipeByTitle(title);
//        System.out.println(recipe);
//        model.addAttribute("recipe", recipe);
//        return "recipe";
//    }

    @PostMapping("/filter")
    public String filter(@RequestParam String title, Model model) {
        List<Recipe> recipes;
        if (title != null && !title.isEmpty()) {
            recipes = recipeService.findRecipesByTitle(title);
            System.out.println("Title is " + title);
        } else {
            recipes = recipeService.findAll();
        }
        model.addAttribute("recipes", recipes);
        return "recipes";
    }


}
