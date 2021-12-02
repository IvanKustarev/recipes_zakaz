package com.example.recipes.controller;

import com.example.recipes.model.Event;
import com.example.recipes.model.Ingredient;
import com.example.recipes.model.User;
import com.example.recipes.service.EventService;
import com.example.recipes.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final EventService eventService;

    public UserController(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @GetMapping("{id}")
    public String findUserById(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("author", user);
        return "userProfile";
    }

    @GetMapping("{id}/favoriteRecipes")
    public String getUserFavoriteRecipes(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("author", user);
        return "userFavoriteRecipes";
    }

    @GetMapping("{id}/shoppingList")
    public String getUserShoppingList(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("author", user);
        return "userShoppingList";
    }

    @PostMapping("{userId}/shoppingList/{ingredientId}")
    public String getUserShoppingList(@PathVariable Long userId, @PathVariable Long ingredientId, Model model) {
        User user = userService.findById(userId);
        Iterator<Ingredient> iterator = user.getIngredientsToBuy().iterator();
        while (iterator.hasNext()) {
            Ingredient ingredient = iterator.next();
            if (ingredient.getId().equals(ingredientId)) {
                iterator.remove();
            }
        }

        model.addAttribute("author", user);
        return "userShoppingList";
    }

    @GetMapping("{id}/eventsPlan")
    public String getUserEventsPlan(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("author", user);
        return "userEventsPlan";
    }

    @PostMapping("{id}/eventsPlan/addEvent")
    public String addEvent(@PathVariable Long id, Model model, @RequestParam("eventText") String eventText) {
        User user = userService.findById(id);
//        user.addEvent(new Event(eventText));
        eventService.saveEvent(new Event(user, eventText));
        userService.saveUser(user);
        model.addAttribute("author", user);
        return "userEventsPlan";
    }

    @PostMapping("{userId}/eventsPlan/{eventId}/delete")
    public String getUserEventsPlan(@PathVariable Long userId, @PathVariable Long eventId, Model model) {
        User user = userService.findById(userId);

        eventService.removeById(eventId);

        model.addAttribute("author", user);
        return "userEventsPlan";
    }

    @GetMapping("{id}/editProfile")
    public String getEditProfile(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("author", user);
        return "userEditProfile";
    }

    @PostMapping("/{id}/editProfile/changeUsername")
    public String changeUsername(@PathVariable Long id, Model model, @RequestParam("username") String username) {
        User user = userService.findById(id);
        user.setUsername(username);
        userService.saveUser(user);
        model.addAttribute("author", user);
        return "userProfile";
    }

    @PostMapping("/{id}/editProfile/changePassword")
    public String changePassword(@PathVariable Long id, Model model, @RequestParam("password") String password) {
        User user = userService.findById(id);
        user.setPassword(password);
        userService.saveUser(user);
        model.addAttribute("author", user);
        return "userProfile";
    }
}
