package com.example.recipes.controller;

import com.example.recipes.model.Meal;
import com.example.recipes.model.Recipe;
import com.example.recipes.model.User;
import com.example.recipes.service.impl.MealServiceImpl;
import com.example.recipes.service.impl.RecipeServiceImpl;
import org.dom4j.rule.Mode;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/meals")
public class MealController {

    private final MealServiceImpl mealService;
    private final RecipeServiceImpl recipeService;

    public MealController(MealServiceImpl mealService, RecipeServiceImpl recipeService) {
        this.mealService = mealService;
        this.recipeService = recipeService;
    }


    @GetMapping("/create")
    public String planMeal(@AuthenticationPrincipal User user,Model model) {
        model.addAttribute("author", user);
        return "createMeal";
    }

    @GetMapping
    public String findAll(@AuthenticationPrincipal User user,Model model) {
        List<Meal> meals = mealService.findAll();
        model.addAttribute("meals", meals);
        model.addAttribute("author", user);
        return "meals";
    }

    @PostMapping("/{mealId}/recipe/create")
    public String saveRecipe(@PathVariable Long mealId, @AuthenticationPrincipal User user,
                             @RequestParam String name, @RequestParam String description) {
        Recipe recipe = new Recipe(name, description, user);
        Meal meal = mealService.findById(mealId);
        List<Recipe> dishes = meal.getDishes();
        dishes.add(recipe);
        meal.setDishes(dishes);
        mealService.save(meal);
        return "redirect:/meals/";
    }

    @GetMapping("/{mealId}/recipe/create")
    public String saveRecipePage(@AuthenticationPrincipal User user,@PathVariable Long mealId, Model model) {
        model.addAttribute("mealId", mealId);
        model.addAttribute("author", user);
        return "recipeToMeal";
    }

    @PostMapping("/create")
    public String saveMeal(@AuthenticationPrincipal User user,
                           @RequestParam String name) {
        Meal meal = new Meal(name, user);
        mealService.createMeal(meal);
        return "redirect:/meals/" + meal.getId();
    }

    @GetMapping("/{id}")
    public String findById(@AuthenticationPrincipal User user,@PathVariable Long id, Model model) {
        Meal meal = mealService.findById(id);
        model.addAttribute("meal", meal);
        model.addAttribute("author", user);
        return "meal";
    }
}
