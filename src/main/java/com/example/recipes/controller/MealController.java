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
    public String planMeal() {
        return "createMeal";
    }

    @GetMapping
    public String findAll(Model model) {
        List<Meal> meals = mealService.findAll();
        model.addAttribute("meals", meals);
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
        System.out.println("added meal " + mealService.findById(mealId));
        return "redirect:/meals/";
    }

    @GetMapping("/{mealId}/recipe/create")
    public String saveRecipePage(@PathVariable Long mealId, Model model) {
        model.addAttribute("mealId", mealId);
        return "recipeToMeal";
    }

    @PostMapping("/create")
    public String saveMeal(@AuthenticationPrincipal User user,
                           @RequestParam String name) {
        Meal meal = new Meal(name, user);
        mealService.createMeal(meal);
//        return "meals";
        return "redirect:/meals/" + meal.getId();
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        Meal meal = mealService.findById(id);
//        System.out.println(meal);
        System.out.println(meal);
        model.addAttribute("meal", meal);
        return "meal";
    }
}
