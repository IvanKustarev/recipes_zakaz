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

import java.util.Iterator;
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
    public String findAll(@AuthenticationPrincipal User user,Model model) {
//        initDb();
        List<Recipe> allRecipes = recipeService.findAll();
        model.addAttribute("recipes", allRecipes);
        model.addAttribute("author", user);
        return "recipes";
    }

    @GetMapping("/create")
    public String createRecipe(@AuthenticationPrincipal User user,Model model) {
        model.addAttribute("author", user);
        return "createRecipe";
    }

    @PostMapping("/create")
    public String saveRecipe(@AuthenticationPrincipal User user,
                             @RequestParam String title, @RequestParam String description, @RequestParam String ingredients, Model model) {
        Recipe recipe = new Recipe(title, description, user, ingredients);
        recipeService.createRecipe(recipe);
        model.addAttribute("author", user);
        return "redirect:/";
    }

    @GetMapping("/recipe/{id}")
    public String getRecipeById(@AuthenticationPrincipal User user1,@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findRecipeById(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("author", user1);
        User user = userService.findByUsername(user1.getUsername());
        String favoriteClass = "";
        Iterator<Recipe> recipeIterator = user.getFavoriteRecipes().iterator();
        while (recipeIterator.hasNext()){
            if(recipeIterator.next().equals(recipe)){
                favoriteClass = "btn btn-danger";
            }
        }
        if(favoriteClass.equals("")){
            favoriteClass = "btn btn-primary";
        }
        String addToSoppingListClass = "";
        Iterator<Ingredient> ingredientIterator = user.getIngredientsToBuy().iterator();
        while (ingredientIterator.hasNext()){
            if(ingredientIterator.next().equals(recipe.getIngredients()[0])){
                addToSoppingListClass = "btn btn-danger";
            }
        }
        if(addToSoppingListClass.equals("")){
            addToSoppingListClass = "btn btn-primary";
        }
        ButtonClass buttonClass = new ButtonClass(favoriteClass, addToSoppingListClass);
        model.addAttribute("buttons", buttonClass);
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
        model.addAttribute("author", user);
        return "redirect:/recipe/" + id;
    }

    @GetMapping("/recipe/{id}/toShoppingList")
    public String addToShoppingList(@AuthenticationPrincipal User user1,
                              @PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findRecipeById(id);
        User user = userService.findByUsername(user1.getUsername());
        user.addIngredientsToBuy(recipe.getIngredients()[0]);
        userService.saveUser(user);
        model.addAttribute("recipe", recipe);
        model.addAttribute("author", user);
        return "redirect:/recipe/" + id;
    }


    @PostMapping("/filter")
    public String filter(@AuthenticationPrincipal User user,@RequestParam String title, Model model) {
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
        model.addAttribute("author", user);
        return "recipes";
    }

    public class ButtonClass{
        private String favorite;
        private String addToShoppingList;

        public ButtonClass() {
        }

        public ButtonClass(String favorite, String addToShoppingList) {
            this.favorite = favorite;
            this.addToShoppingList = addToShoppingList;
        }

        public String getFavorite() {
            return favorite;
        }

        public void setFavorite(String favorite) {
            this.favorite = favorite;
        }

        public String getAddToShoppingList() {
            return addToShoppingList;
        }

        public void setAddToShoppingList(String addToShoppingList) {
            this.addToShoppingList = addToShoppingList;
        }
    }
}
